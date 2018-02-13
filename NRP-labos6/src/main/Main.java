package main;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Rule;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class Main {

	public static void main(String[] args) {

		String fileName = "fcl/sport.fcl";
		FIS fis = FIS.load(fileName, true);
		if (fis == null) {
			System.err.println("Can't load file: '" + fileName + "'");
			return;
		}
		
		JFuzzyChart.get().chart(fis);
		
		fis.setVariable("razinaAktivnosti", 8);
		fis.setVariable("brojSuigraca", 15);
		fis.setVariable("temperatura", 20);
		fis.setVariable("strahOdVisine", 4);
		fis.setVariable("cijena", 3);
		fis.setVariable("znanjePlivanja", 4);

		fis.evaluate();
		
		Variable sport = fis.getVariable("decision");
        JFuzzyChart.get().chart(sport, sport.getDefuzzifier(), true);
		
		System.out.println(fis);
		System.out.println("REZULTAT:");
		System.out.println(fis.getVariable("decision").getValue());
		
		for (Rule r : fis.getFunctionBlock("sportovi").getFuzzyRuleBlock("pravilaSporta").getRules()) {
			System.out.println(r);
		}

	}

}
