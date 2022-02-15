package hu.progmatic.carnivora.kepkezeles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class KepService {

  @Autowired
  private KepRepository kepRepository;

  public List<KepDto> getAllKepDto() {
    return kepRepository.findAll().stream()
        .map(
        kep -> KepDto.builder()
            .id(kep.getId())
            .megnevezes(kep.getMegnevezes())
            .meret(meretFormazas(kep.getMeret()))
            .build()
    ).toList();
  }

  public String meretFormazas(Long meret) {
    if (meret > 1024 * 1024) {
      return (meret / (1024 * 1024)) + " MB";
    }
    if (meret > 1024) {
      return (meret / 1024) + " KB";
    }
    return meret + " B";
  }

  public KepMegjelenitesDto getKepMegjelenitesDto(Integer kepId) {
    Kep kep = kepRepository.getById(kepId);
    return KepMegjelenitesDto.builder()
        .contentType(kep.getContentType())
        .kepAdat(kep.getKepAdat())
        .build();
  }

  public void saveKep(KepFeltoltesCommand kepFeltoltesCommand) {
    try {
      MultipartFile kepFile = kepFeltoltesCommand.getKepFile();
      Kep kep = Kep.builder()
          .contentType(kepFile.getContentType())
          .meret(kepFile.getSize())
          .kepAdat(kepFile.getBytes())
          .megnevezes(kepFeltoltesCommand.getMegnevezes())
          .build();
      kepRepository.save(kep);
    } catch (IOException e) {
      throw new KepFeltoltesHibaException();
    }
  }

  public void deleteKep(Integer kepId) {
    kepRepository.deleteById(kepId);
  }

}
