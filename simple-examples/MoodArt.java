void main() {
    var optionsText = """
            How are you feeling today?
             1 - Happy
             2 - Sad
             3 - Angry
             4 - Confused
             5 - You don't know?
            Enter your choice:\s""";

    var asciiArt = switch (IO.readln(optionsText).toLowerCase()) {
        case "1", "happy" -> HappyScene;
        case "2", "sad" -> SadScene;
        case "3", "angry" -> AngryScene;
        case "4", "5", "confused", "i don't know" -> ConfusedScene;
        default -> NeutralScene;
    };
    IO.println(asciiArt);
}

private static final String HappyScene = """
        	\\(^_^)/        🌞☁️
        	 / | \\        🌳 🌳
        	  | |      🏡   🚶‍♂️   🌷🌼
        	_/   \\_       🚶‍♀️   🐦
        A bright sunny day, kids are playing, birds are singing!""";

private static final String SadScene = """
        	(T_T)         ☁️☁️🌧️
        	/ |\\         🌳  🚶‍♂️  🌲
        	| |       🌼    💧💧   🌸
        	/   \\      🏠   🚶‍♀️
        It's a rainy evening, people walk quietly, lost in thought...""";

private static final String AngryScene = """
            (╬ಠ益ಠ)       ⚡⚡
            --|--      🌲🔥🌲
                / \\       🚗💨🚗💨
        The streets are chaotic, cars honking, lightning strikes!""";

private static final String ConfusedScene = """
            ¯\\_(ツ)_/¯     🛤️🔀
            /  |  \\     🌳🚶‍♂️🌳
                o   o      🏡  🌼
        You stand at a crossroads, unsure of which path to take.""";

private static final String NeutralScene = """
            (•_•)      🌅🌳
            -| | -     🏠🚶‍♂️
            /   \\     🌸  🚶‍♀️
        Just another normal day, the world moves as usual...""";
