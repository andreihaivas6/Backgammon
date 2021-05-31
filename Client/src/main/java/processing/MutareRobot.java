package processing;

import item.Dice;
import item.Triangle;
import screen.GameScreen;

import java.util.List;
import java.util.Objects;

public class MutareRobot {
    private Triangle triangleFrom, triangleTo;
    private Dice dice;

    public MutareRobot() {}

    public MutareRobot(Triangle triangleFrom, Triangle triangleTo, Dice dice) {
        this.triangleFrom = triangleFrom;
        this.triangleTo = triangleTo;
        this.dice = dice;
    }

    public static MutareRobot robotAI(List<MutareRobot> mutari, GameScreen gameScreen, Integer indexPlayer) {
        /*
         Hard Robot: (in ordinea prioritatilor)
         - cauta piese inamice de mancat, care sunt departe de casa proprie, nu lasa poz veche descoperita
         - cauta piese inamice de mancat, care sunt departe de casa proprie, lasa poz veche descoperita
         - cauta piese proprii de acoperit (triunghiuri cu o singura piesa), fara sa lase pozitia veche descoperita
         - cauta sa mute 2 piese diferite a.i. sa creeze o 'poarta', fara sa lase pozitii vechi descoperite (o singura piesa ramasa)
         - cauta sa mute pe pozitii pe care are deja piese, dar sa nu lase o piesa descoperita pe pozitia veche
         - cauta sa mute 2 piese diferite a.i. sa creeze o 'poarta'(2 piese de acelasi fel), lasa exact o pozitie veche descoperita
         - cauta piese proprii de acoperit (triunghiuri cu o singura piesa), lasa pozitia veche descoperita
         - cauta sa mute 2 piese diferite a.i. sa creeze o 'poarta'(2 piese de acelasi fel), lasa ambele pozitii vechi descoperite
         - cauta sa mute pe pozitii pe care are deja piese, chiar daca lasa descoperite
         - cauta piese inamice de mancat in casa proprie
         - muta pe o pozitie aleatorie
         */
        System.out.println("alege mutare robot");
        if (Main.vsComputeEasy) {
            return mutari.get((int) (Math.random() * mutari.size()));
        } else {
            // cauta piese de mancat care sunt departe de casa proprie, nu lasa pozitia veche descoperita
            for (MutareRobot mutareRobot : mutari) {
                Triangle triangleFrom = mutareRobot.getTriangleFrom();
                Triangle triangleTo = mutareRobot.getTriangleTo();
                if (triangleTo.getIndexPlayer() == (indexPlayer + 1) % 2 && triangleTo.getNumberPieces() == 1
                        && triangleTo.getValoare() <= 18 && triangleFrom.getNumberPieces() > 2) {
                    return mutari.get(mutari.indexOf(mutareRobot));
                }
            }

            // cauta piese de mancat care sunt departe de casa proprie, dar lasa pozitia veche descoperita
            for (MutareRobot mutareRobot : mutari) {
                Triangle triangleTo = mutareRobot.getTriangleTo();
                if (triangleTo.getIndexPlayer() == (indexPlayer + 1) % 2 && triangleTo.getNumberPieces() == 1
                        && triangleTo.getValoare() <= 18) {
                    return mutari.get(mutari.indexOf(mutareRobot));
                }
            }

            // cauta piese de ale lui de acoperit, fara sa lase pozitia veche descoperita
            for (MutareRobot mutareRobot : mutari) {
                Triangle triangleFrom = mutareRobot.getTriangleFrom();
                Triangle triangleTo = mutareRobot.getTriangleTo();
                if (triangleTo.getIndexPlayer() == indexPlayer && triangleTo.getNumberPieces() == 1
                        && triangleFrom.getNumberPieces() > 2) {
                    return mutari.get(mutari.indexOf(mutareRobot));
                }
            }

            // cauta sa mute 2 piese diferite a.i. sa creeze o 'poarta', fara sa lase pozitii vechi descoperite
            for (MutareRobot mutareRobot : mutari) {
                for (MutareRobot mutareRobot1 : mutari) {
                    if (!mutareRobot.equals(mutareRobot1)) {
                        Triangle triangleTo1 = mutareRobot.getTriangleTo();
                        Triangle triangleTo2 = mutareRobot1.getTriangleTo();
                        Triangle triangleFrom1 = mutareRobot.getTriangleFrom();
                        Triangle triangleFrom2 = mutareRobot1.getTriangleFrom();
                        if (gameScreen.getDisponibility() >= 2 &&
                                triangleTo1.getValoare() == triangleTo2.getValoare() &&
                                triangleTo1.getNumberPieces() <= 1 &&
                                (triangleFrom1.getNumberPieces() > 2 &&
                                        triangleFrom2.getNumberPieces() > 2)) {
                            return mutari.get(mutari.indexOf(mutareRobot));
                        }
                    }
                }
            }

            // cauta pozitii cu piese de ale lui, a.i. dupa mutare sa nu le lase descoperite
            for (MutareRobot mutareRobot : mutari) {
                Triangle triangleTo = mutareRobot.getTriangleTo();
                if (triangleTo.getIndexPlayer() == indexPlayer && triangleTo.getNumberPieces() != 1
                        && mutareRobot.getTriangleFrom().getNumberPieces() > 2) {
                    return mutari.get(mutari.indexOf(mutareRobot));
                }
            }

            // cauta sa mute 2 piese diferite a.i. sa creeze o 'poarta', si lasa o singura pozitie veche descoperita
            for (MutareRobot mutareRobot : mutari) {
                for (MutareRobot mutareRobot1 : mutari) {
                    if (!mutareRobot.equals(mutareRobot1)) {
                        Triangle triangleTo1 = mutareRobot.getTriangleTo();
                        Triangle triangleTo2 = mutareRobot1.getTriangleTo();
                        Triangle triangleFrom1 = mutareRobot.getTriangleFrom();
                        Triangle triangleFrom2 = mutareRobot1.getTriangleFrom();
                        if (gameScreen.getDisponibility() >= 2 &&
                                triangleTo1.getValoare() == triangleTo2.getValoare() &&
                                triangleTo1.getNumberPieces() <= 1 &&
                                (triangleFrom1.getNumberPieces() > 2 ||
                                        triangleFrom2.getNumberPieces() > 2)) {
                            return mutari.get(mutari.indexOf(mutareRobot));
                        }
                    }
                }
            }

            // cauta piese de ale lui de acoperit, dar lasa pozitia veche descoperita
            for (MutareRobot mutareRobot : mutari) {
                Triangle triangleTo = mutareRobot.getTriangleTo();
                if (triangleTo.getIndexPlayer() == indexPlayer && triangleTo.getNumberPieces() == 1) {
                    return mutari.get(mutari.indexOf(mutareRobot));
                }
            }

            // cauta sa mute 2 piese diferite a.i. sa creeze o 'poarta'(2 piese de acelasi fel), si lasa ambele pozitii vechi descoperite
            for (MutareRobot mutareRobot1 : mutari) {
                for (MutareRobot mutareRobot2 : mutari) {
                    if (!mutareRobot1.equals(mutareRobot2)) {
                        Triangle triangleTo1 = mutareRobot1.getTriangleTo();
                        Triangle triangleTo2 = mutareRobot2.getTriangleTo();
                        if (gameScreen.getDisponibility() >= 2 &&
                                triangleTo1.getValoare() == triangleTo2.getValoare() &&
                                triangleTo1.getNumberPieces() == 0) {
                            return mutari.get(mutari.indexOf(mutareRobot1));
                        }
                    }
                }
            }

            // cauta pozitii cu piese de ale lui, dar lasa descoperite
            for (MutareRobot mutareRobot : mutari) {
                Triangle triangleTo = mutareRobot.getTriangleTo();
                if (triangleTo.getIndexPlayer() == indexPlayer && triangleTo.getNumberPieces() != 1) {
                    return mutari.get(mutari.indexOf(mutareRobot));
                }
            }

            // cauta piese de mancat care sunt in propria casa
            for (MutareRobot mutareRobot : mutari) {
                Triangle triangleTo = mutareRobot.getTriangleTo();
                if (triangleTo.getIndexPlayer() == (indexPlayer + 1) % 2 && triangleTo.getNumberPieces() == 1) {
                    return mutari.get(mutari.indexOf(mutareRobot));
                }
            }

            return mutari.get((int) (Math.random() * mutari.size()));// altfel muta pe o pozitie random goala
        }
    }

    public Triangle getTriangleFrom() {
        return triangleFrom;
    }

    public void setTriangleFrom(Triangle triangleFrom) {
        this.triangleFrom = triangleFrom;
    }

    public Triangle getTriangleTo() {
        return triangleTo;
    }

    public void setTriangleTo(Triangle triangleTo) {
        this.triangleTo = triangleTo;
    }

    public Dice getDice() {
        return dice;
    }

    public void setDice(Dice dice) {
        this.dice = dice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MutareRobot that = (MutareRobot) o;
        return triangleFrom.equals(that.triangleFrom) && triangleTo.equals(that.triangleTo) && dice.equals(that.dice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(triangleFrom, triangleTo, dice);
    }
}
