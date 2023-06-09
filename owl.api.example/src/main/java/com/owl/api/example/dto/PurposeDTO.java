package com.owl.api.example.dto;

import lombok.Data;
@Data
public class PurposeDTO {
    private MembershipDTO appDevelopment;
    private MembershipDTO gaming;
    private MembershipDTO cryptoMining;
    private MembershipDTO home;
    private MembershipDTO business;
    private MembershipDTO hosting;
}
