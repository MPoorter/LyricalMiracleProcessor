package be.matthiasdepoorter.utilities;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.fop.apps.MimeConstants;
import org.xml.sax.SAXException;

public final class CreatePdfUtility {

	private static CreatePdfUtility instance;

	private CreatePdfUtility() {

	}

	public static synchronized CreatePdfUtility getInstance() {
		if (instance == null) {
			instance = new CreatePdfUtility();
		}
		return instance;
	}

	public <T> void createPdf(Class<T> clazz, T song, File location) throws SAXException, IOException, ConfigurationException, JAXBException, TransformerException {
		JAXBContext context = JAXBContext.newInstance(clazz);
		Marshaller marshaller = context.createMarshaller();
		StringWriter stringWriter = new StringWriter();
		marshaller.marshal(song, stringWriter);

		TransformerFactory factory = TransformerFactory.newInstance();
		Source xslt = new StreamSource("src/main/resources/Song2PDF.xslt");
		Transformer transformer = factory.newTransformer(xslt);
		StreamSource rss = new StreamSource(new StringReader(stringWriter.toString()));

		DefaultConfigurationBuilder cfgBuilder = new DefaultConfigurationBuilder();
		Configuration cfg = cfgBuilder.buildFromFile(new File("src/main/resources/mycfg.xml"));
		FopFactoryBuilder fopFactoryBuilder = new FopFactoryBuilder(new File(".").toURI()).setConfiguration(cfg);

		FopFactory fFac = fopFactoryBuilder.build();
		FileOutputStream out = new FileOutputStream(location);
		Fop fop = fFac.newFop(MimeConstants.MIME_PDF, out);
		Result pdf = new SAXResult(fop.getDefaultHandler());
		transformer.transform(rss, pdf);

		if (Desktop.isDesktopSupported()) {
			Desktop.getDesktop().open(location);
		}
	}
}
