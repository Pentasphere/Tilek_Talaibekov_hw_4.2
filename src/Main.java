import java.util.Objects;
import java.util.Random;

public class Main {
    public static int bossHealth = 900;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {253, 353, 453, 303, 103, 153};
    public static int[] heroesDamage = {10, 15, 20, 0, 25, 30};
    public static String[] heroesAttackType =
            {"Physical", "Magical", "Kinetic", "Medic", "Lucky", "Thor"};
    public static int roundNumber = 0;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            playRound();
        }
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        bossHits();
        lucky();
        medicTreat();
        heroesHit();
        thor();
        printStatistics();
    }



    public static void thor() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesAttackType[i].equals("Thor") && heroesHealth[i] > 0) {
                Random random = new Random();
                boolean thorHit = random.nextBoolean();
                if (thorHit) {
                    bossDamage = 0;
                    System.out.println("Thor stunned");
                } else {
                    bossDamage = 50;
                }
            }
        }
    }

    public static void lucky() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesAttackType[i].equals("Lucky") && heroesHealth[i] > 0) {
                Random random = new Random();
                boolean luckyHit = random.nextBoolean();
                if (luckyHit) {
                    heroesHealth[i] = heroesHealth[i] + bossDamage;
                    System.out.println("Lucky dodged");
                }
            }
        }
    }

    public static void medicTreat() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesAttackType[i].equals("Medic") && heroesHealth[i] > 0) {
                Random random = new Random();
                int randomHealth = random.nextInt(heroesAttackType.length);
                int healthCoeff = random.nextInt(5) + 1;
                if (!Objects.equals(heroesAttackType[randomHealth], "Medic") && heroesHealth[randomHealth] > 0 && heroesHealth[randomHealth] < 100) {
                    heroesHealth[randomHealth] = heroesHealth[randomHealth] + healthCoeff;
                    System.out.println("Medic treats: " + heroesAttackType[randomHealth] + " for " + healthCoeff);
                    /*break;*/
                }
            }
        }

    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (bossDefence == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(8) + 2; // 2,3,4,5,6,7,8,9
                    damage = damage * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth -= damage;
                }
            }
        }
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] -= bossDamage; // heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ---------------");
        /*String defence;
        if (bossDefence == null) {
            defence = "No defence";
        } else {
            defence = bossDefence;
        }*/
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage + " defence: "
                + (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]
                    + " damage: " + heroesDamage[i]);
        }
    }
}
