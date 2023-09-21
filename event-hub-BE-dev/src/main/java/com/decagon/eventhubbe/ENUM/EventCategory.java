package com.decagon.eventhubbe.ENUM;
public enum
EventCategory {
    FOOD_AND_DRINKS("Food & Drinks"),
    FILM_MEDIA_AND_ENTERTAINMENT("Film Media & Entertainment"),
    EVENT_AND_LIFESTYLE("Event & Lifestyle"),
    SPECIAL_INTEREST("Special Interest"),
    RELIGIOUS_AND_SPIRITUALITY("Religious & Spirituality"),
    TECHNOLOGY("Technology"),
    GOVERNMENT_AND_POLITICS("Government & Politics"),
    EDUCATION("Education");
    private final String displayName;
    EventCategory(String displayName) {
        this.displayName = displayName;
    }
    public static EventCategory fromDisplayName(String displayName) {
        for (EventCategory category : EventCategory.values()) {
            if (category.displayName.equalsIgnoreCase(displayName)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No category found with the display name: " + displayName);
    }







}
