import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LocalDateTime localDateTime = LocalDateTime.parse(scanner.nextLine());
        int hours = scanner.nextInt();
        int minutes = scanner.nextInt();
        LocalDateTime localDateTime1 = localDateTime.minus(hours, ChronoUnit.HOURS);
        LocalDateTime localDateTime2 = localDateTime1.plus(minutes, ChronoUnit.MINUTES);
        System.out.printf(localDateTime2.toString());
    }
}