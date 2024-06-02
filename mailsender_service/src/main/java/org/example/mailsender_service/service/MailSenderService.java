package org.example.mailsender_service.service;


import org.example.mailsender_service.model.Bill;
import org.example.mailsender_service.model.Client;
import org.example.mailsender_service.model.MailStructure;
import org.example.mailsender_service.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("$(Vé Xe Đắt)")
    String fromMail;
    public void sendMail(Integer billId){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        String urlBill = "http://localhost:8765/bill-service/bill/get?id=" + billId;
        Bill bill = restTemplate.getForObject(urlBill, Bill.class);

        String urlClient = "http://localhost:8765/client-service/client/get?id=" + bill.getIdClient();
        Client client = restTemplate.getForObject(urlClient, Client.class);

        String urlTrip = "http://localhost:8765/trip-service/trip/get?id=" + bill.getIdTrip();
        Trip trip = restTemplate.getForObject(urlTrip, Trip.class);

        MailStructure mailStructure = new MailStructure();
        mailStructure.setSubject("THÔNG BÁO BILL ĐẶT VÉ XE");
        String message = "DEAR " + client.getName() +"\n"+
                "Chúng tôi xác nhận bạn đã đặt vé xe của nhà xe " + trip.getName() + " gồm các thông tin sau :\n"+
                "   - Biển số xe: " + trip.getBienSo()+ "\n" +
                "   - Khởi hành: "+ trip.getDon() + " tại " + bill.getDon() + " vào lúc: " + trip.getDonTime() +"\n"+
                "   - Điểm đến: "+ trip.getTra() + " tại " + bill.getTra() + " vào lúc: " + trip.getTraTime() +"\n"+
                "   - Giá vé: " + trip.getPrice() +"đ/vé \n" +
                "   - Số lượng vé: " + bill.getQuantity() +"\n"+
                "   - Tổng tiền đã thanh toán: "+ trip.getPrice() * bill.getQuantity() + "đ\n"+
                "\nVui lòng liên hệ nhà xe qua số điện thoại "+ trip.getPhone() + " nếu có bất kì thắc mắc nào\n\n"+
                "CẢM ƠN ĐÃ SỬ DỤNG DỊCH VỤ CỦA CHÚNG TÔI";
        mailStructure.setMessage(message);

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromMail);
        simpleMailMessage.setSubject(mailStructure.getSubject());
        simpleMailMessage.setText(mailStructure.getMessage());
        simpleMailMessage.setTo(client.getEmail());
        mailSender.send(simpleMailMessage);
    }
}
