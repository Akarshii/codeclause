import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void clrScr() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

    public static void main(String[] args) {
        clrScr();
        int toDo = Logo.frtScr();
        if (toDo == 1) {
            LockingUnlocking.Lock();
        } else if (toDo == 2) {
            LockingUnlocking.Unllock();
        } else {
            clrScr();
            System.exit(0);
        }
    }
}

class LockingUnlocking {
    public static void Unllock() {
        Main.clrScr();
        Logo.ulkLogo();
        try {
            String userHome = System.getProperty("user.home");
            String input = userHome + File.separator + "Documents" + File.separator + "Locked123";
            String output = userHome + File.separator + "Documents" + File.separator + "Unlocked";
            FolderZipUtil.unzipFolder(input, output);
            FolderZipUtil.unzipFolder(input, output);
            FolderZipUtil.deleteFolder(input);
        } catch (Exception ignored) {
        }
        Logo.encrLogo(2);
        Main.main(null);
    }

    public static void Lock() {
        Main.clrScr();
        Logo.lkLogo();
        String path2Encrypt = PathReleted.lkFilePath();
        if (path2Encrypt.equals("-1")) {
            Main.main(null);
        } else {
            Main.clrScr();
            Logo.encrLogo(1);
            try {
                String userHome = System.getProperty("user.home");
                String documentsDirectory = userHome + File.separator + "Documents" + File.separator + "Locked123";
                FolderZipUtil.zipFolder(path2Encrypt, documentsDirectory);
                Logo.encrLogo(2);

            } catch (IOException e) {
                Main.main(null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Main.main(null);
        }
    }
}


class FolderZipUtil {
    public static void zipFolder(String sourceFolderPath, String outputZipFilePath) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(outputZipFilePath);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            File sourceFolder = new File(sourceFolderPath);
            zipFile(sourceFolder, sourceFolder.getName(), zos);
        }
    }

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zos) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zos.putNextEntry(new ZipEntry(fileName));
                zos.closeEntry();
            } else {
                zos.putNextEntry(new ZipEntry(fileName + "/"));
                zos.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            assert children != null;
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zos);
            }
            return;
        }
        try (FileInputStream fis = new FileInputStream(fileToZip)) {
            ZipEntry zipEntry = new ZipEntry(fileName);
            zos.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }
        }
    }

    public static void unzipFolder(String zipFilePath, String outputFolder) throws Exception {
        try (FileInputStream fis = new FileInputStream(zipFilePath);
             ZipInputStream zis = new ZipInputStream(fis)) {

            byte[] buffer = new byte[1024];
            ZipEntry zipEntry = zis.getNextEntry();
            while (zipEntry != null) {
                String entryName = zipEntry.getName();
                File newFile = new File(outputFolder + File.separator + entryName);

                if (zipEntry.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    newFile.getParentFile().mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        int bytesRead;
                        while ((bytesRead = zis.read(buffer)) != -1) {
                            fos.write(buffer, 0, bytesRead);
                        }
                    }
                }

                zis.closeEntry();
                zipEntry = zis.getNextEntry();
            }
        }
    }

    public static void deleteFolder(String folderPath) throws IOException {
        Path directory = Paths.get(folderPath);
        Files.walk(directory)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }
}

class Logo {
    public static int frtScr() {
        Main.clrScr();
        Scanner scanner = new Scanner(System.in);
        System.out.print(ConsoleColors.BLUE_BOLD);
        String str = """
                                
                  █████▒▒█████   ██▓    ▓█████▄ ▓█████  ██▀███      ██▓     ▒█████   ▄████▄   ██ ▄█▀▓█████  ██▀███ \s
                ▓██   ▒▒██▒  ██▒▓██▒    ▒██▀ ██▌▓█   ▀ ▓██ ▒ ██▒   ▓██▒    ▒██▒  ██▒▒██▀ ▀█   ██▄█▒ ▓█   ▀ ▓██ ▒ ██▒
                ▒████ ░▒██░  ██▒▒██░    ░██   █▌▒███   ▓██ ░▄█ ▒   ▒██░    ▒██░  ██▒▒▓█    ▄ ▓███▄░ ▒███   ▓██ ░▄█ ▒
                ░▓█▒  ░▒██   ██░▒██░    ░▓█▄   ▌▒▓█  ▄ ▒██▀▀█▄     ▒██░    ▒██   ██░▒▓▓▄ ▄██▒▓██ █▄ ▒▓█  ▄ ▒██▀▀█▄ \s
                ░▒█░   ░ ████▓▒░░██████▒░▒████▓ ░▒████▒░██▓ ▒██▒   ░██████▒░ ████▓▒░▒ ▓███▀ ░▒██▒ █▄░▒████▒░██▓ ▒██▒
                 ▒ ░   ░ ▒░▒░▒░ ░ ▒░▓  ░ ▒▒▓  ▒ ░░ ▒░ ░░ ▒▓ ░▒▓░   ░ ▒░▓  ░░ ▒░▒░▒░ ░ ░▒ ▒  ░▒ ▒▒ ▓▒░░ ▒░ ░░ ▒▓ ░▒▓░
                 ░       ░ ▒ ▒░ ░ ░ ▒  ░ ░ ▒  ▒  ░ ░  ░  ░▒ ░ ▒░   ░ ░ ▒  ░  ░ ▒ ▒░   ░  ▒   ░ ░▒ ▒░ ░ ░  ░  ░▒ ░ ▒░
                 ░ ░   ░ ░ ░ ▒    ░ ░    ░ ░  ░    ░     ░░   ░      ░ ░   ░ ░ ░ ▒  ░        ░ ░░ ░    ░     ░░   ░\s
                           ░ ░      ░  ░   ░       ░  ░   ░            ░  ░    ░ ░  ░ ░      ░  ░      ░  ░   ░    \s
                                         ░                                          ░                              \s
                """;
        System.out.print(str + "\n\n\n");
        System.out.print(ConsoleColors.CYAN_BOLD);
        System.out.print("----------\nOptions :-\n----------\n");
        System.out.println("1) Lock");
        System.out.println("2) Unlock");
        System.out.println("3) Exit");
        System.out.print("\nChoice (1/3): " + ConsoleColors.RESET);
        String choice = scanner.next();
        int rType = -1;
        try {
            char a;
            a = choice.charAt(0);
            if (a != '1' && a != '2' && a != '3') {
                Main.clrScr();
                frtScr();
            } else {
                rType = a - '0';
            }
        } catch (Exception e) {
            frtScr();
        }
        return rType;
    }

    public static void ulkLogo() {
        String str = """
                                
                ██╗   ██╗███╗   ██╗██╗      ██████╗  ██████╗██╗  ██╗███████╗██████╗\s
                ██║   ██║████╗  ██║██║     ██╔═══██╗██╔════╝██║ ██╔╝██╔════╝██╔══██╗
                ██║   ██║██╔██╗ ██║██║     ██║   ██║██║     █████╔╝ █████╗  ██████╔╝
                ██║   ██║██║╚██╗██║██║     ██║   ██║██║     ██╔═██╗ ██╔══╝  ██╔══██╗
                ╚██████╔╝██║ ╚████║███████╗╚██████╔╝╚██████╗██║  ██╗███████╗██║  ██║
                 ╚═════╝ ╚═╝  ╚═══╝╚══════╝ ╚═════╝  ╚═════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝
                                                                                   \s
                """;

        String instruction = """
                ---------------
                Instructions :-
                ---------------
                1) Those Document which You want to Lock/Unlock copy paste to Documents Folder.
                2) It Should be folder only.
                3) After Locking it it wont be recovered without Password.
                4) Dont Rename encrypted file name.
                5) Program will generate encrypted file with Locked so u can't lock 2 file without removing 1st Locked file.
                            
                """;
        System.out.println(ConsoleColors.BLUE_BRIGHT + str + "\n\n" + ConsoleColors.RED_BOLD + instruction + ConsoleColors.RESET);
    }

    public static void encrLogo(int i) {
        if (i == 1) {
            String str = """
                                    
                     /$$$$$$$$                                                     /$$     /$$                   \s
                    | $$_____/                                                    | $$    |__/                   \s
                    | $$       /$$$$$$$   /$$$$$$$  /$$$$$$  /$$   /$$  /$$$$$$  /$$$$$$   /$$ /$$$$$$$   /$$$$$$\s
                    | $$$$$   | $$__  $$ /$$_____/ /$$__  $$| $$  | $$ /$$__  $$|_  $$_/  | $$| $$__  $$ /$$__  $$
                    | $$__/   | $$  \\ $$| $$      | $$  \\__/| $$  | $$| $$  \\ $$  | $$    | $$| $$  \\ $$| $$  \\ $$
                    | $$      | $$  | $$| $$      | $$      | $$  | $$| $$  | $$  | $$ /$$| $$| $$  | $$| $$  | $$
                    | $$$$$$$$| $$  | $$|  $$$$$$$| $$      |  $$$$$$$| $$$$$$$/  |  $$$$/| $$| $$  | $$|  $$$$$$$
                    |________/|__/  |__/ \\_______/|__/       \\____  $$| $$____/    \\___/  |__/|__/  |__/ \\____  $$
                                                             /$$  | $$| $$                               /$$  \\ $$
                                                            |  $$$$$$/| $$                              |  $$$$$$/
                                                             \\______/ |__/                               \\______/\s
                    """;
            System.out.println(ConsoleColors.BLUE_BRIGHT + str + ConsoleColors.RESET);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ignored) {
            }
        } else {
            Main.clrScr();
            String str = """
                                        
                    $$$$$$$\\                                          $$$\\  \s
                    $$  __$$\\                                          \\$$\\ \s
                    $$ |  $$ | $$$$$$\\  $$$$$$$\\   $$$$$$\\        $$\\   \\$$\\\s
                    $$ |  $$ |$$  __$$\\ $$  __$$\\ $$  __$$\\       \\__|   $$ |
                    $$ |  $$ |$$ /  $$ |$$ |  $$ |$$$$$$$$ |             $$ |
                    $$ |  $$ |$$ |  $$ |$$ |  $$ |$$   ____|      $$\\   $$  |
                    $$$$$$$  |\\$$$$$$  |$$ |  $$ |\\$$$$$$$\\       \\__|$$$  /\s
                    \\_______/  \\______/ \\__|  \\__| \\_______|          \\___/ \s
                                                                            \s
                                                                            \s
                                                                            \s
                    """;
            System.out.println(ConsoleColors.BLUE_BRIGHT + str + ConsoleColors.RESET);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }
    }

    public static void lkLogo() {
        String str = """
                                
                ██╗      ██████╗  ██████╗██╗  ██╗███████╗██████╗\s
                ██║     ██╔═══██╗██╔════╝██║ ██╔╝██╔════╝██╔══██╗
                ██║     ██║   ██║██║     █████╔╝ █████╗  ██████╔╝
                ██║     ██║   ██║██║     ██╔═██╗ ██╔══╝  ██╔══██╗
                ███████╗╚██████╔╝╚██████╗██║  ██╗███████╗██║  ██║
                ╚══════╝ ╚═════╝  ╚═════╝╚═╝  ╚═╝╚══════╝╚═╝  ╚═╝
                                                                \s
                """;

        String instruction = """
                ---------------
                Instructions :-
                ---------------
                1) Those Document which You want to Lock/Unlock copy paste to Documents Folder.
                2) It Should be folder only.
                3) After Locking it it wont be recovered without Password.
                4) Dont Rename encrypted file name.
                5) Program will generate encrypted file with Locked so u can't lock 2 file without removing 1st Locked file.
                                
                """;
        System.out.println(ConsoleColors.BLUE_BRIGHT + str + "\n\n" + ConsoleColors.RED_BOLD + instruction + ConsoleColors.RESET);
    }
}

class PathReleted {
    public static String lkFilePath() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(ConsoleColors.WHITE + "------------");
        System.out.println(ConsoleColors.CYAN + "Files are :-");
        System.out.println(ConsoleColors.WHITE + "------------\n" + ConsoleColors.CYAN);
        Map<Integer, String> map = new HashMap<>();
        String userHome = System.getProperty("user.home");
        String documentsDirectory = userHome + File.separator + "Documents";
        File directory = new File(documentsDirectory);
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            int i = 1;
            assert files != null;
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println(i + ") " + file.getName());
                    map.put(i, file.getAbsolutePath());
                    ++i;
                }
            }
            map.put(404, "-1");
        } else {
            System.out.println("Current path is not a directory.");
        }
        System.out.println("404) Previous Page.");
        System.out.print(ConsoleColors.WHITE + "\nChoice: ");
        String rType = "";
        try {
            int ch = scanner.nextInt();
            if (map.containsKey(ch)) {
                rType = map.get(ch);
            } else {
                LockingUnlocking.Lock();
            }
        } catch (Exception e) {
            LockingUnlocking.Lock();
        }
        return rType;
    }
}

class ConsoleColors {
    // Reset
    public static final String RESET = "\033[0m";  // Text Reset

    // Regular Colors
    public static final String BLACK = "\033[0;30m";   // BLACK
    public static final String RED = "\033[0;31m";     // RED
    public static final String GREEN = "\033[0;32m";   // GREEN
    public static final String YELLOW = "\033[0;33m";  // YELLOW
    public static final String BLUE = "\033[0;34m";    // BLUE
    public static final String PURPLE = "\033[0;35m";  // PURPLE
    public static final String CYAN = "\033[0;36m";    // CYAN
    public static final String WHITE = "\033[0;37m";   // WHITE

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m";  // BLACK
    public static final String RED_BOLD = "\033[1;31m";    // RED
    public static final String GREEN_BOLD = "\033[1;32m";  // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m";   // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m";   // CYAN
    public static final String WHITE_BOLD = "\033[1;37m";  // WHITE

    // Underline
    public static final String BLACK_UNDERLINED = "\033[4;30m";  // BLACK
    public static final String RED_UNDERLINED = "\033[4;31m";    // RED
    public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
    public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
    public static final String BLUE_UNDERLINED = "\033[4;34m";   // BLUE
    public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
    public static final String CYAN_UNDERLINED = "\033[4;36m";   // CYAN
    public static final String WHITE_UNDERLINED = "\033[4;37m";  // WHITE

    // Background
    public static final String BLACK_BACKGROUND = "\033[40m";  // BLACK
    public static final String RED_BACKGROUND = "\033[41m";    // RED
    public static final String GREEN_BACKGROUND = "\033[42m";  // GREEN
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
    public static final String BLUE_BACKGROUND = "\033[44m";   // BLUE
    public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
    public static final String CYAN_BACKGROUND = "\033[46m";   // CYAN
    public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE

    // High Intensity
    public static final String BLACK_BRIGHT = "\033[0;90m";  // BLACK
    public static final String RED_BRIGHT = "\033[0;91m";    // RED
    public static final String GREEN_BRIGHT = "\033[0;92m";  // GREEN
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    public static final String BLUE_BRIGHT = "\033[0;94m";   // BLUE
    public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
    public static final String CYAN_BRIGHT = "\033[0;96m";   // CYAN
    public static final String WHITE_BRIGHT = "\033[0;97m";  // WHITE

    // Bold High Intensity
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m";  // CYAN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

    // High Intensity backgrounds
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m";  // CYAN
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m";   // WHITE
}