package hu.progmatic.klad;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/*
Szükség lesz:

KladDtoForJson:
KladService lekérdezi a KladEntity Listát, majd átalakítja KladDtoForJson Listává (id, name, parent). KladForJsonService
ezt kérdezi le a KladServicetől, majd ezt átalakítja genogram annotált Json Stringre.

KladService:
public List<KladDtoForJson> getAllKladDtoForJson (List<KladEntity> input)
    private KladDtoForJson buildKladDtoForJson (KladEntity input)
    private KladEntity buildKladEntityFromKladDtoForJson (KladDtoForJson input)

KladServiceForJson:
    public KladDtoForJson getKladDtoForJsonFromJson (String json)
    public String getJsonFromKladDtoForJsonList (List<KladDtoForJson> input)
 */


@Service
@Transactional
public class KladServiceForJson {
}
