package com.bgokce.eventmanagement.DTO;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {

    @NotEmpty(message = "Etkinlik ismi boş bırakılamaz.")
    private String eventName;

    @NotEmpty(message = "Şehir ismi boş bırakılamaz.")
    private String city;

    @Min(value = 1, message = "Kapasite 1'den az olamaz.")
    private int capacity;

    @NotNull(message = "Etkinlik başlangıç tarihi boş bırakılamaz.")
    private LocalDateTime startDate;

    @NotNull(message = "Etkinlik bitiş tarihi boş bırakılamaz.")
    private LocalDateTime endDate;

    private List<String> questions;

    @NotNull(message = "Etkinlik konum bilgileri boş bırakılamaz.")
    private String latitude;

    @NotNull(message = "Etkinlik konum bilgileri boş bırakılamaz.")
    private String longitude;
}
