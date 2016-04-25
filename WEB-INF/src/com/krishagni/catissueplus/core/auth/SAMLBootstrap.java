package com.krishagni.catissueplus.core.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import javax.servlet.Filter;
import javax.servlet.ServletException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.velocity.app.VelocityEngine;
import org.opensaml.Configuration;
import org.opensaml.PaosBootstrap;
import org.opensaml.saml2.metadata.provider.HTTPMetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.saml2.metadata.provider.ResourceBackedMetadataProvider;
import org.opensaml.util.resource.ClasspathResource;
import org.opensaml.util.resource.ResourceException;
import org.opensaml.xml.ConfigurationException;
import org.opensaml.xml.parse.ParserPool;
import org.opensaml.xml.parse.StaticBasicParserPool;
import org.opensaml.xml.parse.XMLParserException;
import org.opensaml.xml.security.keyinfo.NamedKeyInfoGeneratorManager;
import org.opensaml.xml.security.x509.X509KeyInfoGeneratorFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.access.BootstrapException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.saml.SAMLAuthenticationProvider;
import org.springframework.security.saml.SAMLConstants;
import org.springframework.security.saml.SAMLDiscovery;
import org.springframework.security.saml.SAMLEntryPoint;
import org.springframework.security.saml.SAMLProcessingFilter;
import org.springframework.security.saml.context.SAMLContextProviderImpl;
import org.springframework.security.saml.key.JKSKeyManager;
import org.springframework.security.saml.key.KeyManager;
import org.springframework.security.saml.log.SAMLDefaultLogger;
import org.springframework.security.saml.metadata.CachingMetadataManager;
import org.springframework.security.saml.metadata.ExtendedMetadata;
import org.springframework.security.saml.metadata.ExtendedMetadataDelegate;
import org.springframework.security.saml.metadata.MetadataDisplayFilter;
import org.springframework.security.saml.metadata.MetadataGenerator;
import org.springframework.security.saml.metadata.MetadataGeneratorFilter;
import org.springframework.security.saml.processor.HTTPArtifactBinding;
import org.springframework.security.saml.processor.HTTPPAOS11Binding;
import org.springframework.security.saml.processor.HTTPPostBinding;
import org.springframework.security.saml.processor.HTTPRedirectDeflateBinding;
import org.springframework.security.saml.processor.HTTPSOAP11Binding;
import org.springframework.security.saml.processor.SAMLBinding;
import org.springframework.security.saml.processor.SAMLProcessorImpl;
import org.springframework.security.saml.trust.httpclient.TLSProtocolConfigurer;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;
import org.springframework.security.saml.util.VelocityFactory;
import org.springframework.security.saml.websso.ArtifactResolutionProfileImpl;
import org.springframework.security.saml.websso.WebSSOProfile;
import org.springframework.security.saml.websso.WebSSOProfileConsumer;
import org.springframework.security.saml.websso.WebSSOProfileConsumerHoKImpl;
import org.springframework.security.saml.websso.WebSSOProfileConsumerImpl;
import org.springframework.security.saml.websso.WebSSOProfileECPImpl;
import org.springframework.security.saml.websso.WebSSOProfileImpl;
import org.springframework.security.saml.websso.WebSSOProfileOptions;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.krishagni.catissueplus.core.auth.services.impl.UserAuthenticationServiceImpl;

import edu.emory.mathcs.backport.java.util.Collections;

public class SAMLBootstrap implements InitializingBean {

	@Autowired
	private SAMLUserDetailsService userSvc;

	@Autowired
	private UserAuthenticationServiceImpl userAuthService;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private FilterChainProxy samlFilter;

	private boolean samlEnable;

	private String defaultIdp;

	private String idpMetadataURL;

	private String spMetadataPath;

	private String keyStorePath;

	private String keyStroreDefaultKey;

	private String keyStorePassword;

	private VelocityEngine velocityEngine;

	private StaticBasicParserPool parserPool;

	private HttpClient httpClient;

	private KeyManager keyManager;

	private WebSSOProfileConsumerImpl webSSOprofileConsumer;

	private WebSSOProfileConsumerHoKImpl hokWebSSOprofileConsumer;

	private SAMLDefaultLogger samlLogger;

	private SAMLAuthenticationProvider samlAuthenticationProvider;

	private SAMLContextProviderImpl contextProvider;

	private WebSSOProfile webSSOprofile;

	private WebSSOProfileECPImpl ecpprofile;

	private SAMLEntryPoint samlEntryPoint;

	private CachingMetadataManager metadata;

	private MetadataDisplayFilter metadataDisplayFilter;

	private SAMLProcessingFilter samlWebSSOProcessingFilter;

	private SAMLProcessorImpl processor;

	private HTTPSOAP11Binding soapBinding;

	//private MetadataGenerator metadataGenerator;

	//private MetadataGeneratorFilter metadataGeneratorFilter;

	public boolean isSamlEnable() {
		return samlEnable;
	}

	public void setSamlEnable(boolean samlEnable) {
		this.samlEnable = samlEnable;
	}

	public String getDefaultIdp() {
		return defaultIdp;
	}

	public void setDefaultIdp(String defaultIdp) {
		this.defaultIdp = defaultIdp;
	}

	public String getIdpMetadataURL() {
		return idpMetadataURL;
	}

	public void setIdpMetadataURL(String idpMetadataURL) {
		this.idpMetadataURL = idpMetadataURL;
	}

	public String getSpMetadataPath() {
		return spMetadataPath;
	}

	public void setSpMetadataPath(String spMetadataPath) {
		this.spMetadataPath = spMetadataPath;
	}

	public String getKeyStorePath() {
		return keyStorePath;
	}

	public void setKeyStorePath(String keyStorePath) {
		this.keyStorePath = keyStorePath;
	}

	public String getKeyStroreDefaultKey() {
		return keyStroreDefaultKey;
	}

	public void setKeyStroreDefaultKey(String keyStroreDefaultKey) {
		this.keyStroreDefaultKey = keyStroreDefaultKey;
	}

	public String getKeyStorePassword() {
		return keyStorePassword;
	}

	public void setKeyStorePassword(String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (!samlEnable) {
			return;
		}

		try {
			PaosBootstrap.bootstrap();
			setMetadataKeyInfoGenerator();
		} catch (ConfigurationException e) {
			throw new BootstrapException("Error invoking OpenSAML bootstrap", e);
		}

		samlFilter();
		//metadataGeneratorFilter();
		((ProviderManager)authManager).getProviders().add(getSamlAuthenticationProvider());
	}

	private void setMetadataKeyInfoGenerator() {
		NamedKeyInfoGeneratorManager manager = Configuration.getGlobalSecurityConfiguration().getKeyInfoGeneratorManager();
		X509KeyInfoGeneratorFactory generator = new X509KeyInfoGeneratorFactory();
		generator.setEmitEntityCertificate(true);
		generator.setEmitEntityCertificateChain(true);
		manager.registerFactory(SAMLConstants.SAML_METADATA_KEY_INFO_GENERATOR, generator);
	}

	/**
	 * Define the security filter chain in order to support SSO Auth by using SAML 2.0
	 *
	 * @return Filter chain proxy
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	private FilterChainProxy samlFilter() throws Exception {
		Map<RequestMatcher, List<Filter>> filterChainMap = new HashMap<RequestMatcher, List<Filter>>();
		filterChainMap.put(new AntPathRequestMatcher("/saml/login/**"), Collections.singletonList(getSamlEntryPoint()));
		filterChainMap.put(new AntPathRequestMatcher("/saml/SSO/**"), Collections.singletonList(getSamlWebSSOProcessingFilter()));
		filterChainMap.put(new AntPathRequestMatcher("/saml/metadata/**"), Collections.singletonList(getMetadataDisplayFilter()));

		samlFilter.setFilterChainMap(filterChainMap);
		return samlFilter;
	}

	// Entry point to initialize authentication, default values taken from
	// properties file
	private SAMLEntryPoint getSamlEntryPoint() throws Exception {
		if(samlEntryPoint == null) {
			samlEntryPoint = new SAMLEntryPoint();
			samlEntryPoint.setMetadata(getMetadata());
			samlEntryPoint.setDefaultProfileOptions(getDefaultWebSSOProfileOptions());
			samlEntryPoint.setWebSSOprofile(getWebSSOprofile());
			samlEntryPoint.setWebSSOprofileECP(getEcpprofile());
			samlEntryPoint.setSamlLogger(getSamlLogger());
			samlEntryPoint.setContextProvider(getContextProvider());
			samlEntryPoint.setSamlDiscovery(getSamlIDPDiscovery());

			samlEntryPoint.afterPropertiesSet();
		}
		return samlEntryPoint;
	}

	// Processing filter for WebSSO profile messages
	private SAMLProcessingFilter getSamlWebSSOProcessingFilter() throws Exception {
		if (samlWebSSOProcessingFilter == null) {
			samlWebSSOProcessingFilter = new SAMLProcessingFilter();
			samlWebSSOProcessingFilter.setSAMLProcessor(getProcessor());
			samlWebSSOProcessingFilter.setContextProvider(getContextProvider());
			samlWebSSOProcessingFilter.setAuthenticationManager(authManager);
			samlWebSSOProcessingFilter.setAuthenticationSuccessHandler(getSuccessRedirectHandler());
			samlWebSSOProcessingFilter.setAuthenticationFailureHandler(getAuthenticationFailureHandler());

			samlWebSSOProcessingFilter.afterPropertiesSet();
		}
		return samlWebSSOProcessingFilter;
	}

	private MetadataDisplayFilter getMetadataDisplayFilter() throws Exception {
		if (metadataDisplayFilter == null) {
			metadataDisplayFilter = new MetadataDisplayFilter();
			metadataDisplayFilter.setManager(getMetadata());
			metadataDisplayFilter.setKeyManager(getKeyManager());
			metadataDisplayFilter.setContextProvider(getContextProvider());

			metadataDisplayFilter.afterPropertiesSet();
		}
		return metadataDisplayFilter;
	}

	private WebSSOProfileECPImpl getEcpprofile() throws Exception {
		if (ecpprofile == null) {
			ecpprofile = new WebSSOProfileECPImpl();
			ecpprofile.setProcessor(getProcessor());
			ecpprofile.setMetadata(getMetadata());
		}
		return ecpprofile;
	}

	private WebSSOProfile getWebSSOprofile() throws Exception {
		if (webSSOprofile == null) {
			webSSOprofile = new WebSSOProfileImpl(getProcessor(), getMetadata());
		}
		return webSSOprofile;
	}

	private SAMLProcessorImpl getProcessor() {
		if (processor == null) {
			Collection<SAMLBinding> bindings = new ArrayList<SAMLBinding>();
			bindings.add(getPostBinding());
			bindings.add(getRedirectDeflateBinding());
			bindings.add(getArtifactBinding(getParserPool(), getVelocityEngine()));
			bindings.add(getSoapBinding());
			bindings.add(getPAOS11Binding());
			processor = new SAMLProcessorImpl(bindings);
		} 

		return processor;
	}

	private SAMLContextProviderImpl getContextProvider() throws Exception {
		if (contextProvider == null) {
			contextProvider = new SAMLContextProviderImpl();
			contextProvider.setKeyManager(getKeyManager());
			contextProvider.setMetadata(getMetadata());
			contextProvider.afterPropertiesSet();
		}
		return contextProvider;
	}

	private CachingMetadataManager getMetadata() throws Exception {
		if (metadata == null) {
			List<MetadataProvider> providers = new ArrayList<MetadataProvider>();
			providers.add(getSpExtendedMetadataProvider());
			providers.add(getSsoCircleExtendedMetadataProvider());

			metadata = new CachingMetadataManager(providers);
			metadata.setDefaultIDP("http://idp.ssocircle.com");
			metadata.setKeyManager(getKeyManager());
			metadata.setTLSConfigurer(getTlsProtocolConfigurer());
			metadata.afterPropertiesSet();
		}
		return metadata;
	}

	private TLSProtocolConfigurer getTlsProtocolConfigurer() throws Exception {
		TLSProtocolConfigurer tlsProtocolConfigurer =  new TLSProtocolConfigurer();
		tlsProtocolConfigurer.setKeyManager(keyManager);
		tlsProtocolConfigurer.afterPropertiesSet();
		return tlsProtocolConfigurer;
	}

	private SAMLAuthenticationProvider getSamlAuthenticationProvider() throws ServletException {
		if (samlAuthenticationProvider == null) {
			samlAuthenticationProvider = new SAMLAuthenticationProvider();
			samlAuthenticationProvider.setUserDetails(userSvc);
			samlAuthenticationProvider.setForcePrincipalAsString(false);
			samlAuthenticationProvider.setConsumer(getWebSSOprofileConsumer());
			samlAuthenticationProvider.setHokConsumer(getHokWebSSOprofileConsumer());
			samlAuthenticationProvider.setSamlLogger(getSamlLogger());
			samlAuthenticationProvider.afterPropertiesSet();
		}
		return samlAuthenticationProvider;
	}

	private SAMLDefaultLogger getSamlLogger() {
		if (samlLogger == null) {
			samlLogger = new SAMLDefaultLogger();
		}
		return samlLogger;
	}

	private WebSSOProfileConsumerHoKImpl getHokWebSSOprofileConsumer() {
		if (hokWebSSOprofileConsumer == null) {
			hokWebSSOprofileConsumer = new WebSSOProfileConsumerHoKImpl();
		}
		return hokWebSSOprofileConsumer;
	}

	private WebSSOProfileConsumer getWebSSOprofileConsumer() {
		if (webSSOprofileConsumer == null) {
			webSSOprofileConsumer = new WebSSOProfileConsumerImpl();
		}
		return webSSOprofileConsumer;
	}

	private HTTPArtifactBinding getArtifactBinding(ParserPool parserPool, VelocityEngine velocityEngine) {
		final ArtifactResolutionProfileImpl artifactResolutionProfile = new ArtifactResolutionProfileImpl(getHttpClient());
		artifactResolutionProfile.setProcessor(new SAMLProcessorImpl(getSoapBinding()));

		return new HTTPArtifactBinding(getParserPool(), getVelocityEngine(), artifactResolutionProfile);
	}

	private HTTPSOAP11Binding getSoapBinding() {
		if (soapBinding == null) {
			soapBinding = new HTTPSOAP11Binding(getParserPool());
		}
		return soapBinding;
	}

	private HTTPPAOS11Binding getPAOS11Binding() {
		return new HTTPPAOS11Binding(getParserPool());
	}

	private HTTPRedirectDeflateBinding getRedirectDeflateBinding() {
		return new HTTPRedirectDeflateBinding(getParserPool());
	}

	private HTTPPostBinding getPostBinding() {
		return new HTTPPostBinding(getParserPool(), getVelocityEngine());
	}

	private KeyManager getKeyManager() {
		if (keyManager == null) {
			DefaultResourceLoader loader = new DefaultResourceLoader();
			Resource storeFile = loader.getResource("classpath:" + keyStorePath);
			Map<String, String> passwords = new HashMap<String, String>();
			passwords.put(keyStroreDefaultKey, keyStorePassword);

			keyManager = new JKSKeyManager(storeFile, keyStorePassword, passwords, keyStroreDefaultKey);
		}

		return keyManager;
	}

	private HttpClient getHttpClient() {
		if (httpClient == null) {
			httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
		}
		return httpClient;
	}

	@SuppressWarnings("unchecked")
	private StaticBasicParserPool getParserPool() {
		try {
			if (parserPool == null) {
				parserPool = new StaticBasicParserPool();
				parserPool.setBuilderFeatures(Collections.singletonMap("http://apache.org/xml/features/dom/defer-node-expansion", false));
				parserPool.initialize();
			}
			return parserPool;
		} catch (XMLParserException e) {
			throw new RuntimeException("Error while initilizing parse pool", e);
		}
	}

	private VelocityEngine getVelocityEngine() {
		if (velocityEngine == null) {
			velocityEngine = VelocityFactory.getEngine();
		}
		return velocityEngine;
	}

	/*private MetadataGeneratorFilter metadataGeneratorFilter() throws Exception {
		if (metadataGeneratorFilter == null) {
			metadataGeneratorFilter = new MetadataGeneratorFilter(metadataGenerator());
			metadataGeneratorFilter.setManager(getMetadata());
			metadataGeneratorFilter.setDisplayFilter(getMetadataDisplayFilter());

			metadataGeneratorFilter.afterPropertiesSet();
		}
		return metadataGeneratorFilter;
	}

	private MetadataGenerator metadataGenerator() throws Exception {
		if  (metadataGenerator == null) {
			ExtendedMetadata extendedMetadata = new ExtendedMetadata();
			extendedMetadata.setIdpDiscoveryEnabled(false);

			metadataGenerator = new MetadataGenerator();
			metadataGenerator.setExtendedMetadata(extendedMetadata);
			metadataGenerator.setSamlWebSSOFilter(getSamlWebSSOProcessingFilter());
			metadataGenerator.setSamlEntryPoint(getSamlEntryPoint());
			metadataGenerator.setKeyManager(getKeyManager());
		}
		return metadataGenerator;
	}*/

	private WebSSOProfileOptions getDefaultWebSSOProfileOptions() {
		WebSSOProfileOptions webSSOProfileOptions = new WebSSOProfileOptions();
		webSSOProfileOptions.setIncludeScoping(false);
		return webSSOProfileOptions;
	}

	private SAMLDiscovery getSamlIDPDiscovery() {
		SAMLDiscovery idpDiscovery = new SAMLDiscovery();
		idpDiscovery.setIdpSelectionPath("/saml/idpSelection");
		return idpDiscovery;
	}

	private OsSimpleUrlAuthenticationSuccessHandler getSuccessRedirectHandler() {
		OsSimpleUrlAuthenticationSuccessHandler successRedirectHandler = new OsSimpleUrlAuthenticationSuccessHandler();
		successRedirectHandler.setUserAuthService(userAuthService);
		successRedirectHandler.setDefaultTargetUrl("/#/home");
		return successRedirectHandler;
	}

	private SimpleUrlAuthenticationFailureHandler getAuthenticationFailureHandler() {
		SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();
		failureHandler.setUseForward(true);
		failureHandler.setDefaultFailureUrl("/");
		return failureHandler;
	}

	@SuppressWarnings("deprecation")
	private HTTPMetadataProvider getSsoCircleExtendedMetadataProvider() throws MetadataProviderException {
		HTTPMetadataProvider httpMetadataProvider = new HTTPMetadataProvider(idpMetadataURL, 15000	);
		httpMetadataProvider.setParserPool(getParserPool());

		return httpMetadataProvider;
	}

	private ExtendedMetadataDelegate getSpExtendedMetadataProvider() throws MetadataProviderException, ResourceException {
		ResourceBackedMetadataProvider provider =
				new ResourceBackedMetadataProvider(new Timer(), new ClasspathResource("/openspecimen_app_sp.xml"));
		provider.setParserPool(getParserPool());

		ExtendedMetadata extendedMetadata = new ExtendedMetadata();
		extendedMetadata.setLocal(true);
		extendedMetadata.setSecurityProfile("metaiop");
		extendedMetadata.setSslSecurityProfile("pkix");
		extendedMetadata.setSslHostnameVerification("default");
		extendedMetadata.setSigningKey(keyStroreDefaultKey);
		extendedMetadata.setEncryptionKey(keyStroreDefaultKey);
		extendedMetadata.setRequireArtifactResolveSigned(false);
		extendedMetadata.setRequireLogoutRequestSigned(false);
		extendedMetadata.setRequireLogoutResponseSigned(false);
		extendedMetadata.setIdpDiscoveryEnabled(false);
		extendedMetadata.setSignMetadata(false);

		ExtendedMetadataDelegate extendedMetadataDelegate = new ExtendedMetadataDelegate(provider, extendedMetadata);
		return extendedMetadataDelegate;
	}

}
