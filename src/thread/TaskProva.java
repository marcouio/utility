package thread;

import java.util.ArrayList;
import java.util.List;

public class TaskProva extends Task {

    @Override
    protected List<?> creaRichieste() throws Exception {
        ArrayList<Object> lista = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
                lista.add("thr" + i);
        }
        return lista;
    }

    @Override
    public Class<?> getCallableClass() {
        return RunnerCiao.class;
    }

}
