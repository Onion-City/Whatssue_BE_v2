package GDG.whatssue.domain.user.service;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class MyMessageService {
    private final DefaultMessageService defalutMessageService;
    private String apiKey;
    private String apiSecret;
    private String fromNumber;
    public MyMessageService(@Value("${coolsms.api.key}") String apiKey, @Value("${coolsms.api.secret}") String apiSecret, @Value("${coolsms.api.number}") String fromNumber) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.fromNumber = fromNumber;
        this.defalutMessageService = NurigoApp.INSTANCE.initialize(apiKey,apiSecret,"https://api.coolsms.co.kr");
        System.out.println("messageService : " + defalutMessageService);
    }
    /*
      단일 메시지 발송 예제
     */
    public SingleMessageSentResponse sendOne(String toNumber) {
        Message message = new Message();
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.

        message.setFrom(fromNumber);
        message.setTo(toNumber);
        message.setText("인증번호:"+ randomNumber() + "입니다. 정확히 입력해주세요. 그리고 방구를 드십시오");

        SingleMessageSentResponse response = this.defalutMessageService.sendOne(new SingleMessageSendingRequest(message));
        System.out.println(response);
        return response;
    }
    public int randomNumber() {
        Random random = new Random();
        int randomNum = random.nextInt(899) + 100;
        return randomNum;

    }

}
