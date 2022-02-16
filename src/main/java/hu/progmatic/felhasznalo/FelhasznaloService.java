package hu.progmatic.felhasznalo;

import hu.progmatic.felhasznalo.token.MegerositoToken;
import hu.progmatic.felhasznalo.token.MegerositoTokenRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Log
@Transactional
public class FelhasznaloService implements InitializingBean {

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
    public List<Felhasznalo> findAll() {
        return felhasznaloRepository.findAll();
    }

    private void initFelhasznalo(InitFelhasznalo initFelhasznalo) {
        Felhasznalo felhasznalo = Felhasznalo.builder()
                .felhasznaloNev(initFelhasznalo.getFelhasznaloNev())
                .jelszo(encoder.encode(initFelhasznalo.getJelszo()))
                .role(initFelhasznalo.getRole())
                .engedelyezve(initFelhasznalo.isEngedelyezve())
                .build();
        felhasznaloRepository.save(felhasznalo);
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
                .build();
        String token = UUID.randomUUID().toString();
        MegerositoToken megerositoToken = MegerositoToken.builder()
                .felhasznalo(felhasznalo)
                .lejaratDatuma(LocalDateTime.now().plusMinutes(20))
                .token(token)
                .build();
        megerositoTokenRepository.save(megerositoToken);
        felhasznaloRepository.save(felhasznalo);
        String link = "http://localhost:8084/felhasznalo/confirm?token=" + token;
        emailSenderService.emailKuldes(felhasznalo.getEmail(),
                "Regisztrációt megerősítő email",
                EmailSenderService.emailBodyBuilder(felhasznalo.getFelhasznaloNev(), link));
    }


    @RolesAllowed(UserType.Roles.USER_WRITE_ROLE)
    public void delete(Long id) {
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
                    .keresztNev("admin")
                    .vezetekNev("admin")
                    .email("carnivora.project@gmail.com")
                    .engedelyezve(true)
                    .role(UserType.ADMIN)
                    .build();
            Felhasznalo user = Felhasznalo.builder()
                    .felhasznaloNev("user")
                    .jelszo(encoder.encode("userpass"))
                    .keresztNev("user")
                    .vezetekNev("user")
                    .email("carnivora.project@gmail.com")
                    .engedelyezve(false)
                    .role(UserType.USER)
                    .build();
            felhasznaloRepository.save(admin);
            felhasznaloRepository.save(user);
            //add(new UjFelhasznaloCommand("admin", "adminpass", UserType.ADMIN));
            //add(new UjFelhasznaloCommand("user", "user", UserType.USER));
            //add(new UjFelhasznaloCommand("guest", "guest", UserType.GUEST));
        }
    }

    public boolean hasRole(String role) {
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

    public String getFelhasznaloNev() {
        if (isAnonymusUser()) { return "Vendég"; }
        else {
            MyUserDetails userPrincipal = getMyUserDetails();
            return userPrincipal.getUsername();
        }
    }

    public boolean isAnonymusUser() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser");
    }
}
