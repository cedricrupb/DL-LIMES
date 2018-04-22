package com.github.cedricupb.config;

import com.github.cedricupb.io.limes.ILIMESRunner;
import net.minidev.json.writer.BeansMapper;
import org.apache.commons.cli.Options;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.beanutils.BeanDeclaration;
import org.apache.commons.configuration2.beanutils.BeanHelper;
import org.apache.commons.configuration2.beanutils.XMLBeanDeclaration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class ConfigLoadedDLDomain implements IDLDomain{

    private IDLDomain baseDomain;
    private XMLConfiguration config;

    private ILIMESRunner runner;

    // getting the configuration file
    public  ConfigLoadedDLDomain(IDLDomain base, String cfgFile) throws ConfigurationException {
        baseDomain = base;
        config = loadCfg(cfgFile);
    }

    // The function will return the XML configuration file.
    private XMLConfiguration loadCfg(String file) throws ConfigurationException {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<XMLConfiguration> builder =
                new FileBasedConfigurationBuilder<>(XMLConfiguration.class)
                .configure(params.xml().setFileName(file));

        return builder.getConfiguration();
    }


    // Getting the options needed to run the DL Learner .
    @Override
    public Options getCLIOptions() {
        return baseDomain.getCLIOptions();
    }

    //creating the configuration file in run time.
    @Override
    public ILIMESRunner getLimesInterface() {
        if (runner == null){
            BeanDeclaration decl = new XMLBeanDeclaration(config, "limes.connector");
            runner = (ILIMESRunner) BeanHelper.INSTANCE.createBean(decl);
        }
        return runner;
    }
}
