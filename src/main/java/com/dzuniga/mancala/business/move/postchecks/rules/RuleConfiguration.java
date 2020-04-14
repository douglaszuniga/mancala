package com.dzuniga.mancala.business.move.postchecks.rules;

import com.dzuniga.mancala.business.move.postchecks.rules.conditions.Condition;
import com.dzuniga.mancala.business.move.postchecks.rules.consequences.Consequence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RuleConfiguration {
    @Bean
    public Rule extraTurnRule(Condition extraTurnCondition, Consequence extraTurnConsequence) {
       return Rule.ruleOf(extraTurnCondition, extraTurnConsequence);
    }
    @Bean
    public Rule capturePebblesRule(Condition capturePebblesCondition, Consequence capturePebblesConsequence) {
        return Rule.ruleOf(capturePebblesCondition, capturePebblesConsequence);
    }
    @Bean
    public Rule gameEndRule(Condition gameEndCondition, Consequence gameEndConsequence) {
        return Rule.ruleOf(gameEndCondition, gameEndConsequence);
    }
}
