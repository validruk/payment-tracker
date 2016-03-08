package pt;

public class CommandLineProcessor extends Processor {
  
  private final String line;
  
  public CommandLineProcessor(String line) {
    super();
    this.line = line;
  }

  @Override
  public void process() {
    try {
      parseAndProccesValue(line);
    } catch (Exception e) {
      processError("Incorrect input", e);
    }
  }
}
