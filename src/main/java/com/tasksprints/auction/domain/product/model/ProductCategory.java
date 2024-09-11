package com.tasksprints.auction.domain.product.model;

public enum ProductCategory {
    WOMENS_CLOTHING("여성의류"),
    UNDERWEAR_SLEEPWEAR("언더웨어/잠옷"),
    PLUS_SIZE("빅사이즈"),
    SENIOR_CLOTHING("시니어의류"),
    MENS_CLOTHING("남성의류"),

    OVERSEAS_COSMETICS("해외화장품"),
    HAIR("헤어"),
    DOMESTIC_COSMETICS("국내화장품"),
    BODY("바디"),
    PERFUME("향수"),
    BEAUTY("미용"),

    CURTAINS("커튼"),
    FURNITURE("가구"),
    LIVING("생활"),
    DECOR("장식소품"),
    STORAGE("수납용품"),
    KITCHEN("주방"),
    INTERIOR("인테리어"),
    BEDDING("침구"),

    TV("TV"),
    REFRIGERATOR("냉장고"),
    WASHING_MACHINE("세탁기"),
    KITCHEN_APPLIANCES("주방/생활"),
    BEAUTY_APPLIANCES("이미용가전"),
    AIR_PURIFIER("청정/제습"),
    FAN_AIRCON("선풍기/에어컨"),

    MIRRORLESS_CAMERA("미러리스"),
    DSLR("DSLR"),
    DIGITAL_CAMERA("디카"),
    MP3_PMP_DICTIONARY("MP3/PMP/사전"),
    GAMING_CONSOLE("게임기/타이틀"),
    SMARTPHONE("휴대폰/스마트폰"),
    LAPTOP("노트북"),
    DESKTOP("데스크탑"),
    MONITOR("모니터"),
    PRINTER("프린터"),
    ETC("기타제품");
    private final String displayName;

    ProductCategory(String displayName) {
        this.displayName = displayName;
    }

    /**
     * 한정된 갯수의 Category 이기에 따로 Over-head는 발생하지 않을 것으로 판단
     ***/
    public static ProductCategory fromDisplayName(String displayName) {
        if (displayName == null || displayName.trim().isEmpty()) {
            throw new IllegalArgumentException("Display name cannot be null or empty.");
        }

        try {
            return ProductCategory.valueOf(displayName.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid product category: " + displayName);
        }
    }

    public String getDisplayName() {
        return displayName;
    }
}
