package bbrz.textadventure.rules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RuleTest {

    Rule ruleBeach, ruleClearing, ruleCliff, ruleEdgeForest, ruleEdgeSwamp, ruleLake, ruleMeadow, ruleSea, ruleStarting, ruleSwamp, ruleWell, ruleWoods;

    @Test
    void WoodsRuleTests() {
        ruleWoods = new WoodsLocRule();

        assertTrue(ruleWoods.canHandle(MapRuleMark.WOODS));
        assertFalse(ruleWoods.canHandle(MapRuleMark.WELL));

        assertTrue(ruleWoods.isInRules(MapRuleMark.WOODS));
        assertTrue(ruleWoods.isInRules(MapRuleMark.CLEARING));
        assertTrue(ruleWoods.isInRules(MapRuleMark.EDGE_OF_THE_FOREST));
        assertTrue(ruleWoods.isInRules(MapRuleMark.LAKE));
        assertFalse(ruleWoods.isInRules(MapRuleMark.SWAMP));
    }

    @Test
    void WellRuleTests() {
        ruleWell = new WellLocRule();

        assertTrue(ruleWell.canHandle(MapRuleMark.WELL));
        assertFalse(ruleWell.canHandle(MapRuleMark.MEADOW));

        assertTrue(ruleWell.isInRules(MapRuleMark.MEADOW));
        assertTrue(ruleWell.isInRules(MapRuleMark.EDGE_OF_THE_FOREST));
        assertTrue(ruleWell.isInRules(MapRuleMark.EDGE_OF_THE_SWAMP));
        assertFalse(ruleWell.isInRules(MapRuleMark.WELL));
    }

    @Test
    void SwampRuleTests() {
        ruleSwamp = new SwampLocRule();

        assertTrue(ruleSwamp.canHandle(MapRuleMark.SWAMP));
        assertFalse(ruleSwamp.canHandle(MapRuleMark.EDGE_OF_THE_SWAMP));

        assertTrue(ruleSwamp.isInRules(MapRuleMark.SWAMP));
        assertTrue(ruleSwamp.isInRules(MapRuleMark.EDGE_OF_THE_SWAMP));
        assertFalse(ruleSwamp.isInRules(MapRuleMark.MEADOW));
    }

    @Test
    void StartingLocationRuleTests() {
        ruleStarting = new StartingLocRule();

        assertTrue(ruleStarting.canHandle(MapRuleMark.STARTING_LOCATION));
        assertFalse(ruleStarting.canHandle(MapRuleMark.MEADOW));

        assertTrue(ruleStarting.isInRules(MapRuleMark.SEA));
        assertFalse(ruleStarting.isInRules(MapRuleMark.REPLACEABLE));
    }

    @Test
    void SeaRuleTests() {
        ruleSea = new SeaLocRule();

        assertTrue(ruleSea.canHandle(MapRuleMark.SEA));
        assertFalse(ruleSea.canHandle(MapRuleMark.LAKE));

        assertTrue(ruleSea.isInRules(MapRuleMark.SEA));
        assertTrue(ruleSea.isInRules(MapRuleMark.BEACH));
        assertTrue(ruleSea.isInRules(MapRuleMark.CLIFF));
        assertFalse(ruleSea.isInRules(MapRuleMark.WOODS));
    }

    @Test
    void MeadowRuleTests() {
        ruleMeadow = new MeadowLocRule();

        assertTrue(ruleMeadow.canHandle(MapRuleMark.MEADOW));
        assertFalse(ruleMeadow.canHandle(MapRuleMark.LAKE));

        assertTrue(ruleMeadow.isInRules(MapRuleMark.MEADOW));
        assertTrue(ruleMeadow.isInRules(MapRuleMark.LAKE));
        assertTrue(ruleMeadow.isInRules(MapRuleMark.WELL));
        assertTrue(ruleMeadow.isInRules(MapRuleMark.CLIFF));
        assertTrue(ruleMeadow.isInRules(MapRuleMark.EDGE_OF_THE_SWAMP));
        assertTrue(ruleMeadow.isInRules(MapRuleMark.EDGE_OF_THE_FOREST));
        assertFalse(ruleMeadow.isInRules(MapRuleMark.SEA));
    }

    @Test
    void LakeRuleTests() {
        ruleLake = new LakeLocRule();

        assertTrue(ruleLake.canHandle(MapRuleMark.LAKE));
        assertFalse(ruleLake.canHandle(MapRuleMark.REPLACEABLE));

        assertTrue(ruleLake.isInRules(MapRuleMark.MEADOW));
        assertTrue(ruleLake.isInRules(MapRuleMark.WOODS));
        assertTrue(ruleLake.isInRules(MapRuleMark.EDGE_OF_THE_FOREST));
        assertTrue(ruleLake.isInRules(MapRuleMark.EDGE_OF_THE_SWAMP));
        assertFalse(ruleLake.isInRules(MapRuleMark.LAKE));
    }

    @Test
    void EdgeOfTheSwampRuleTests() {
        ruleEdgeSwamp = new EdgeOfTheSwampLocRule();

        assertTrue(ruleEdgeSwamp.canHandle(MapRuleMark.EDGE_OF_THE_SWAMP));
        assertFalse(ruleEdgeSwamp.canHandle(MapRuleMark.REPLACEABLE));

        assertTrue(ruleEdgeSwamp.isInRules(MapRuleMark.SWAMP));
        assertTrue(ruleEdgeSwamp.isInRules(MapRuleMark.MEADOW));
        assertTrue(ruleEdgeSwamp.isInRules(MapRuleMark.CLIFF));
        assertTrue(ruleEdgeSwamp.isInRules(MapRuleMark.LAKE));
        assertTrue(ruleEdgeSwamp.isInRules(MapRuleMark.EDGE_OF_THE_FOREST));
        assertTrue(ruleEdgeSwamp.isInRules(MapRuleMark.EDGE_OF_THE_SWAMP));
        assertFalse(ruleEdgeSwamp.isInRules(MapRuleMark.REPLACEABLE));
    }

    @Test
    void EdgeOfTheForestRuleTests() {
        ruleEdgeForest = new EdgeOfTheForestLocRule();

        assertTrue(ruleEdgeForest.canHandle(MapRuleMark.EDGE_OF_THE_FOREST));
        assertFalse(ruleEdgeForest.canHandle(MapRuleMark.CLIFF));

        assertTrue(ruleEdgeForest.isInRules(MapRuleMark.MEADOW));
        assertTrue(ruleEdgeForest.isInRules(MapRuleMark.LAKE));
        assertTrue(ruleEdgeForest.isInRules(MapRuleMark.WELL));
        assertTrue(ruleEdgeForest.isInRules(MapRuleMark.WOODS));
        assertTrue(ruleEdgeForest.isInRules(MapRuleMark.EDGE_OF_THE_SWAMP));
        assertTrue(ruleEdgeForest.isInRules(MapRuleMark.EDGE_OF_THE_FOREST));
        assertFalse(ruleEdgeForest.isInRules(MapRuleMark.CLIFF));
    }

    @Test
    void CliffRuleTests() {
        ruleCliff = new CliffLocRule();

        assertTrue(ruleCliff.canHandle(MapRuleMark.CLIFF));
        assertFalse(ruleCliff.canHandle(MapRuleMark.LAKE));

        assertTrue(ruleCliff.isInRules(MapRuleMark.BEACH));
        assertTrue(ruleCliff.isInRules(MapRuleMark.EDGE_OF_THE_FOREST));
        assertTrue(ruleCliff.isInRules(MapRuleMark.EDGE_OF_THE_SWAMP));
        assertTrue(ruleCliff.isInRules(MapRuleMark.MEADOW));
        assertTrue(ruleCliff.isInRules(MapRuleMark.WOODS));
        assertTrue(ruleCliff.isInRules(MapRuleMark.CLIFF));
        assertFalse(ruleCliff.isInRules(MapRuleMark.SWAMP));
    }

    @Test
    void ClearingRuleTests() {
        ruleClearing = new ClearingLocRule();

        assertTrue(ruleClearing.canHandle(MapRuleMark.CLEARING));
        assertFalse(ruleClearing.canHandle(MapRuleMark.BEACH));

        assertTrue(ruleClearing.isInRules(MapRuleMark.WOODS));
        assertTrue(ruleClearing.isInRules(MapRuleMark.WELL));
        assertTrue(ruleClearing.isInRules(MapRuleMark.EDGE_OF_THE_FOREST));
        assertFalse(ruleClearing.isInRules(MapRuleMark.CLIFF));
    }

    @Test
    void BeachRuleTests() {
        ruleBeach = new BeachLocRule();

        assertTrue(ruleBeach.canHandle(MapRuleMark.BEACH));
        assertFalse(ruleBeach.canHandle(MapRuleMark.CLIFF));

        assertTrue(ruleBeach.isInRules(MapRuleMark.BEACH));
        assertTrue(ruleBeach.isInRules(MapRuleMark.EDGE_OF_THE_FOREST));
        assertTrue(ruleBeach.isInRules(MapRuleMark.CLIFF));
        assertTrue(ruleBeach.isInRules(MapRuleMark.MEADOW));
        assertTrue(ruleBeach.isInRules(MapRuleMark.SEA));
        assertFalse(ruleBeach.isInRules(MapRuleMark.SWAMP));
    }
}