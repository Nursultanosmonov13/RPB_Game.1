package com.company;

import java.util.Random;

//для теста
public class Main {
    public static int[] heroesHealth = {270, 280, 250, 500, 500, 230, 200, 300}; //Жизнь героев
    public static int[] heroesDamage = {20, 15, 25, 0, 5, 10, 20, 0}; //Удар героев
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Berserk", "Thor"};
    public static int bossHealth = 700; //Жизнь Босса
    public static int bossHDamage = 50; //Удар Босса
    public static String bossDefenceType = "";
    public static int roundNumber = 0;
    public static int antiDamage = 20;


    public static void main(String[] args) {
        while (!isGameFinished()) {
            round();
        }
    }

    public static void round() {
        roundNumber++;
        bossDefenceType = changeBossDefence();
        System.out.println("___________________");
        System.out.println("Boss choose " + bossDefenceType);
        System.out.println("___________________");

        bossHits();
        heroesHits();
        medicToHelp();
        printStatistcs();
    }

    public static boolean isGameFinished() {

        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int j : heroesHealth) {
            if (j > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!");
        }
        return allHeroesDead;
    }

    public static String changeBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);
        return heroesAttackType[randomIndex];
    }

    public static void bossHits() { // Босс атакует
        Random random = new Random();
        int a = random.nextInt(2);
        //System.out.println("test - " + a);
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                if (heroesAttackType[i].equals("Golem")) {
                    heroesHealth[i] = heroesHealth[i] - bossHDamage / 5 * (heroesHealth.length - 1);
                    //System.out.println(heroesAttackType[i] + "---" + heroesHealth[i]);
                } else if (heroesAttackType[i].equals("Lucky") && a == 1) {
                    heroesHealth[i] = heroesHealth[i];
                    //System.out.println(heroesAttackType[i] + "---" + heroesHealth[i]);
                } else if (heroesAttackType[i].equals("Berserk")) {
                    heroesHealth[i] = (heroesHealth[i] - bossHDamage / 2);
                    antiDamage = bossHDamage / 2;
                    //System.out.println(heroesAttackType[i] + "---" + heroesHealth[i] + " anti " + antiDamage);
                } else {
                    //System.out.println("Финиш" + (heroesHealth[i] = heroesHealth[i] - bossHDamage));
                    heroesHealth[i] = heroesHealth[i] - bossHDamage;
                }

                if (heroesHealth[i] < 0) {
                    heroesHealth[i] = 0;
                }

            }
        }
    }

    public static void heroesHits() { //Герои атакует
        Random random = new Random();
        int coeff = random.nextInt(8) + 2;

        for (int i = 0; i < heroesDamage.length; i++) {
            if (bossHealth > 0 && heroesHealth[i] > 0) {
                if (heroesAttackType[i].equals(bossDefenceType)) {
                    if (heroesAttackType[i].equals("Berserk")) {
                        bossHealth = bossHealth - antiDamage - heroesDamage[i] * coeff;
                        // System.out.println("Criticaldamage Berserk: " + heroesDamage[i] * coeff + " = " + coeff);
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                        // System.out.println("Criticaldamage1: " + heroesDamage[i] * coeff + " = " + coeff);
                    }
                } else {
                    if (heroesAttackType[i].equals("Berserk")) {
                        bossHealth = bossHealth - heroesDamage[i];
                        //System.out.println("Criticaldamage Berserk: " + heroesDamage[i]);
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                        // System.out.println("Criticaldamage2: " + heroesDamage[i]);
                    }
                }
                if (bossHealth < 0) {
                    bossHealth = 0;
                }
                //System.out.println(bossHealth);

            }
        }

    }

    public static void medicToHelp() { //медик лечит

        boolean repetitions = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0 && heroesHealth[i] < 100 && repetitions) {
                int healthOfHeroes = 20;
                if (heroesAttackType[i].equals("Medic")) {
                    continue;
                }
                System.out.print("Медик лечит " + heroesAttackType[i] + " жизнь: " + heroesHealth[i] + " + ");
                heroesHealth[i] += healthOfHeroes;
                repetitions = false;
                System.out.println(healthOfHeroes + " = " + heroesHealth[i]);
            }
        }
    }

    public static void printStatistcs() {
        System.out.println("_________Round [" + roundNumber + "]");
        System.out.println("---------------------------------");
        System.out.println("Boss health " + bossHealth + " [" + bossHDamage + "]");
        for (int i = 0; i < heroesAttackType.length; i++) {
            if (heroesHealth[i] > 0) {
                System.out.println(heroesAttackType[i] + " health " + heroesHealth[i] + " [" + heroesDamage[i] + "]");
            }
        }
    }
}
