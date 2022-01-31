package hu.progmatic.klad;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class KladService implements InitializingBean {

    @Autowired
    private KladRepository kladRepository;

    @Override
    public void afterPropertiesSet() {

    }
}
