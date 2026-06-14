void main() {
    var devStyles = Map.ofEntries(
            Map.entry('A', new String[] { "Anxiety Driven Development", "When the deadline is near, all roads lead to it!" }),
            Map.entry('B', new String[] { "Bug Driven Development", "If it compiles, ship it! Fix it later." }),
            Map.entry('C', new String[] { "Conference Driven Development", "You only code after attending a conference!" }),
            Map.entry('D', new String[] { "Deadline Driven Development", "Productivity spikes only near deadlines." }),
            Map.entry('E', new String[] { "Experiment Driven Development", "Keep trying things until something works." }),
            Map.entry('F', new String[] { "Fear Driven Development", "You change code only when your manager isn't watching." }),
            Map.entry('G', new String[] { "Google Driven Development", "If you don't know it, Google it!" }),
            Map.entry('H', new String[] { "Hackathon Driven Development", "You work best when there's free pizza and a timer." }),
            Map.entry('I', new String[] { "Interview Driven Development", "You only study before job interviews." }),
            Map.entry('J', new String[] { "JavaDoc Driven Development", "Code first, documentation later (maybe)." }),
            Map.entry('K', new String[] { "Keyboard Driven Development", "More keystrokes mean better coding!" }),
            Map.entry('L', new String[] { "Log Driven Development", "More logs, more debugging!" }),
            Map.entry('M', new String[] { "Micromanagement Driven Development", "Every line of code must be reviewed twice!" }),
            Map.entry('N', new String[] { "Night Owl Driven Development", "Best code is written at 2 AM." }),
            Map.entry('O', new String[] { "Open Source Driven Development", "If it's open source, it must be good!" }),
            Map.entry('P', new String[] { "Patch Driven Development", "Code first, patch later." }),
            Map.entry('Q', new String[] { "Quick Fix Driven Development", "Make it work first, think later." }),
            Map.entry('R', new String[] { "Refactor Driven Development", "The first version is never good enough." }),
            Map.entry('S', new String[] { "Stack Overflow Driven Development", "90% copy-paste, 10% debugging." }),
            Map.entry('T', new String[] { "Test Driven Development", "Or at least pretending to do so." }),
            Map.entry('U', new String[] { "User Complaint Driven Development", "Fix it only when users notice!" }),
            Map.entry('V', new String[] { "Version Driven Development", "Let's just increment the version number." }),
            Map.entry('W', new String[] { "Wiki Driven Development", "If it’s not on the wiki, does it exist?" }),
            Map.entry('X', new String[] { "XML Driven Development", "Because JSON is too mainstream." }),
            Map.entry('Y', new String[] { "YOLO Driven Development", "Ship now, fix later." }),
            Map.entry('Z', new String[] { "Zero Bug Driven Development", "Close all bugs by marking them 'Won't Fix'." }));
    char input = IO.readln("Enter a letter (A-Z) to find out your Development Style (or 0 to quit game): ")
        .toUpperCase()
        .charAt(0);

    while (input != '0') {
        if (devStyles.containsKey(input)) {
            var messages = devStyles.get(input);
            IO.println("** " + messages[0] + " **");
            IO.println(messages[1]);
        } else
            IO.println("Invalid input! Please enter a letter from A-Z.");
        input = IO.readln("\nEnter a letter (A-Z) to find out your Development Style (or 0 to quit game): ")
            .toUpperCase().charAt(0);
    }
}