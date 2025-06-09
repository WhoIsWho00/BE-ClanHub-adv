package com.example.clanhubadv.entity.enums;

/**
 * Перечисление внутрисемейных ролей, которые могут быть выбраны при регистрации
 */
public enum FamilyRole {
    FATHER("Отец"),
    MOTHER("Мать"),
    DAUGHTER("Дочь"),
    SON("Сын"),
    OTHER("Другое");
    
    private final String displayName;
    
    FamilyRole(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
