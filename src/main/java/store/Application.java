package store;

import camp.nextstep.edu.missionutils.Console;
import store.domain.Stock;
import store.view.FileReader;
import store.view.InputView;
import store.view.OutputView;

import java.io.IOException;
import java.util.Map;

public class Application {
    public static void main(String[] args)  {
        FileReader fileReader = new FileReader();
        Map<String, Stock> map = fileReader.readFile();
        OutputView outputView = new OutputView(map);
        outputView.printProducts();
        //Console.close();
    }
}
