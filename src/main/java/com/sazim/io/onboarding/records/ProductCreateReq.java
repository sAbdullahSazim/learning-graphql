package com.sazim.io.onboarding.records;

import lombok.Builder;

@Builder
public record ProductCreateReq(String name,String description, String imageUrl){}