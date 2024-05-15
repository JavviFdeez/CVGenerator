package org.JavviFdeez.utils;

import org.JavviFdeez.model.entity.Skills;

import java.util.ArrayList;
import java.util.List;

public class SkillsMax {
    private static int skillCount = 0;
    private static final List<Skills> skillList = new ArrayList<>();
    private static final int MAX_SKILLS = 6;

    public void addSkill(Skills skill) {
        if (skillCount < MAX_SKILLS) {
            // Agregar la habilidad
            skillList.add(skill);
            // Incrementar el contador de habilidades
            skillCount++;
        } // No necesitas un else aquí, simplemente no se agregará más habilidades si skillCount es igual o mayor a MAX_SKILLS
    }
}
