package br.com.lkm.taxone.mapper.service;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SmtpEmailService {
	
	@SuppressWarnings("unused")
	private static String BODY = "Bom dia.\r\n"
			+ "\r\n"
			+ "Jason";
	
	@Value("${lkm.taxonemapper.email.smtp.host}")
	private String host;
	
	@Value("${lkm.taxonemapper.email.smtp.username}")
	private String username;

	@Value("${lkm.taxonemapper.email.smtp.password}")
	private String password;

	
	private Session session = null;
	
	@PostConstruct
	public void init() {
		Properties p = new Properties();
		p.setProperty("mail.smtp.ssl.enable", "true");
		p.setProperty("mail.smtp.auth", "true");
		p.setProperty("mail.smtp.host", host);

		Authenticator au = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username,password);
			}
		};
		session = Session.getDefaultInstance(p, au);
	}
	
	public void sendMail(List<String> emails, String subject, String corpo) throws Exception {
		MimeMultipart mm = new MimeMultipart();
		String data = "Bom dia.\r\n\r\nAgendamentos com erro:\r\n" + corpo + "\r\n\r\nAtt.,\r\n\r\nEquipe Mapper"; 
		MimeBodyPart content = new MimeBodyPart(new ByteArrayInputStream((data).getBytes()));
		mm.addBodyPart(content);

		MimeMessage message = new MimeMessage(session);
        message.setFrom( new InternetAddress(username));
		message.setContent(mm);
		emails.stream().forEach(email -> {
			try { message.addRecipients(RecipientType.TO, email); } catch (Exception e) {}
		});
		message.setSubject(subject);
		Transport.send(message);
		log.info("Email enviado:" + emails + " | with assunto:" + subject);
	}

	public static void main(String... args) {
		try {
			SmtpEmailService s = new SmtpEmailService();
			s.host = "smtp.gmail.com";
			s.username = "jasondouglasoliveira33333@gmail.com";
			s.password = "ygwqycjesyhjmphf";
			s.init();
			String corpo = "Erro no processamento do agendamento #id do Agendamento: 31265";
			s.sendMail(Arrays.asList("jasondouglasoliveira333@gmail.com"), "Test", corpo);
		}catch (Exception e) { 
			e.printStackTrace();
		}
	}
}
