package net.soko.digs_and_dunes.core.registry;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;

public class ModCriteria {
    public static void init(){
        // NO-OP
    }

    public static <T extends CriterionTrigger<?>> T register(T trigger) {
        return CriteriaTriggers.register(trigger);
    }

}
