package hu.progmatic.felhasznalo;

import hu.progmatic.felhasznalo.token.MegerositoToken;
import hu.progmatic.felhasznalo.token.MegerositoTokenRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Log
@Transactional
public class FelhasznaloService implements InitializingBean {

    @Value("${spring.serverUrl}")
    private String serverUrl = "http://localhost:8084";
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final FelhasznaloRepository felhasznaloRepository;
    private final PasswordEncoder encoder;
    private final MegerositoTokenRepository megerositoTokenRepository;
    private final EmailSenderService emailSenderService;

    public FelhasznaloService(FelhasznaloRepository felhasznaloRepository,
                              PasswordEncoder encoder,
                              MegerositoTokenRepository megerositoTokenRepository,
                              EmailSenderService emailSenderService) {
        this.felhasznaloRepository = felhasznaloRepository;
        this.encoder = encoder;
        this.megerositoTokenRepository = megerositoTokenRepository;
        this.emailSenderService = emailSenderService;
    }

    @RolesAllowed(UserType.Roles.USER_READ_ROLE)
    public List<FelhasznaloDto> findAll() {
        return felhasznaloRepository.findAll().stream().map(this::felhasznaloDtoBuilder).toList();
    }

    public void ujFelhasznaloValidalas(UjFelhasznaloCommand command) {
        if (felhasznaloRepository.findByFelhasznaloNev(command.getFelhasznaloNev()).isPresent()) {
            throw new FelhasznaloLetrehozasException("Ilyen névvel már létezik felhasználó!");
        }
    }


    public void add(UjFelhasznaloCommand command) {
        Felhasznalo felhasznalo = Felhasznalo.builder()
                .felhasznaloNev(command.getFelhasznaloNev())
                .jelszo(encoder.encode(command.getJelszo()))
                .engedelyezve(false)
                .email(command.getEmail())
                .keresztNev(command.getKeresztNev())
                .vezetekNev(command.getVezetekNev())
                .role(UserType.USER)
                .regisztracioIdeje(LocalDateTime.now())
                .build();
        String token = UUID.randomUUID().toString();
        MegerositoToken megerositoToken = MegerositoToken.builder()
                .felhasznalo(felhasznalo)
                .token(token)
                .build();
        felhasznalo.setToken(megerositoToken);
        megerositoTokenRepository.save(megerositoToken);
        felhasznaloRepository.save(felhasznalo);
        String link = serverUrl + "/felhasznalo/confirm?token=" + token;
        emailSenderService.emailKuldes(felhasznalo.getEmail(),
                "Regisztrációt megerősítő email",
                EmailSenderService.emailBodyBuilder(felhasznalo.getFelhasznaloNev(), link));
    }


    @RolesAllowed(UserType.Roles.USER_WRITE_ROLE)
    public void delete(Long id) {
        Felhasznalo felhasznalo = felhasznaloRepository.getById(id);
        MegerositoToken token = felhasznalo.getToken();
        megerositoTokenRepository.delete(token);
        felhasznaloRepository.deleteById(id);
    }

    @RolesAllowed(UserType.Roles.USER_READ_ROLE)
    public Optional<Felhasznalo> findByName(String nev) {
        return felhasznaloRepository.findByFelhasznaloNev(nev);
    }

    @Override
    public void afterPropertiesSet() {
        if (findAll().isEmpty()) {
            Felhasznalo admin = Felhasznalo.builder()
                    .felhasznaloNev("admin")
                    .jelszo(encoder.encode("adminpass"))
                    .keresztNev("Admin")
                    .vezetekNev("Admin")
                    .email("carnivora.project@gmail.com")
                    .engedelyezve(true)
                    .role(UserType.ADMIN)
                    .regisztracioIdeje(LocalDateTime.now())
                    .megerositesIdeje(LocalDateTime.now())
                    .build();
            Felhasznalo user = Felhasznalo.builder()
                    .felhasznaloNev("user")
                    .jelszo(encoder.encode("userpass"))
                    .keresztNev("Diana")
                    .vezetekNev("User")
                    .email("carnivora.project@gmail.com")
                    .engedelyezve(true)
                    .role(UserType.USER)
                    .regisztracioIdeje(LocalDateTime.now())
                    .megerositesIdeje(LocalDateTime.now())
                    .build();
            felhasznaloRepository.save(admin);
            felhasznaloRepository.save(user);
        }
    }

    public boolean hasRole(String role) {
        if (isAnonymusUser()) { return false; }
        MyUserDetails userPrincipal = getMyUserDetails();
        return userPrincipal.getRole().hasRole(role);
    }

    public Long getFelhasznaloId() {
        if (isAnonymusUser()) { return null; }
        else {
            MyUserDetails userPrincipal = getMyUserDetails();
            return userPrincipal.getFelhasznaloId();
        }
    }

    private MyUserDetails getMyUserDetails() {
        return (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private FelhasznaloDto felhasznaloDtoBuilder(Felhasznalo felhasznalo){
        return FelhasznaloDto.builder()
                .id(felhasznalo.getId())
                .felhasznaloNev(felhasznalo.getFelhasznaloNev())
                .vezetekNev(felhasznalo.getVezetekNev())
                .keresztNev(felhasznalo.getKeresztNev())
                .email(felhasznalo.getEmail())
                .engedelyezve(felhasznalo.isEngedelyezve() ? "Igen" : "Nem")
                .regisztracioIdeje(dateTimeFormatter.format(felhasznalo.getRegisztracioIdeje()))
                .megerositesIdeje(felhasznalo.getMegerositesIdeje() == null ?
                        "Még nem hitelesített" : dateTimeFormatter.format(felhasznalo.getMegerositesIdeje()))
                .role(felhasznalo.getRole())
                .build();
    }

    public String getFelhasznaloNev() {
        if (isAnonymusUser()) { return "Vendég"; }
        else {
            MyUserDetails userPrincipal = getMyUserDetails();
            return userPrincipal.getUsername();
        }
    }

    public String getKeresztNev() {
        if (isAnonymusUser()) { return "Vendég"; }
        else {
            MyUserDetails userPrincipal = getMyUserDetails();
            return userPrincipal.getKeresztNev();
        }
    }

    public boolean isAnonymusUser() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser");
    }

    public FelhasznaloDto findById(Long id) {
        return felhasznaloDtoBuilder(felhasznaloRepository.getById(id));
    }

    public void modositJogosultsag(Jogosultsag jogosultsag) {
        Felhasznalo felhasznalo = felhasznaloRepository.findById(jogosultsag.getFelhasznaloId()).orElseThrow();
        felhasznalo.setRole(UserType.valueOf(jogosultsag.getJog()));
    }
}
