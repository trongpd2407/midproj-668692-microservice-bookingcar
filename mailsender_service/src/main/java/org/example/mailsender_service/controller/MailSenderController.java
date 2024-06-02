package org.example.mailsender_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.example.mailsender_service.model.MailStructure;
import org.example.mailsender_service.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mailsender")
public class MailSenderController {
    @Autowired
    private MailSenderService mailSenderService;

    @Operation(summary = "Send email for a bill", description = "Send an email for a specific bill ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email sent successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid bill ID supplied",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public boolean send(@RequestParam("billId") Integer billId) {
        try {
            mailSenderService.sendMail(billId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
