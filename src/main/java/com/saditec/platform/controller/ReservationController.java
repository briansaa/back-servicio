package com.saditec.platform.controller;

import com.saditec.platform.service.ReservationService;
import com.saditec.platform.type.ApiResponse;
import com.saditec.platform.type.TReservationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ApiResponse<TReservationDto> addReservation(@RequestBody TReservationDto tReservationDto, Principal principal) {
        return new ApiResponse<TReservationDto>()
                .toSuccess(reservationService.addReservation(tReservationDto, principal));
    }
}
