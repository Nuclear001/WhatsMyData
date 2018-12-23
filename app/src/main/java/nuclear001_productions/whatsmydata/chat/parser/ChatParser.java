package nuclear001_productions.whatsmydata.chat.parser;

public interface ChatParser<ReturnType> {
    ReturnType analyzeChat() throws Exception;
}
