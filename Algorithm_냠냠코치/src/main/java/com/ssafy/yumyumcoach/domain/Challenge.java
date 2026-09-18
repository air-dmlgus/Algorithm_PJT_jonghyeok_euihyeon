package com.ssafy.yumyumcoach.domain;

import java.time.LocalDate;
public record Challenge(long id,String title,String description,LocalDate startDate,LocalDate endDate,String imageUrl) {}
