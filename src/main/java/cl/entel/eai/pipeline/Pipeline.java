package cl.entel.eai.pipeline;

import cl.entel.eai.exception.NoDataToReceiveException;
import cl.entel.eai.exception.PipelineException;

public class Pipeline<C, I, O> {
    private final Handler<C, I, O> currentHandler;

    public Pipeline(Handler<C, I, O> handler) {
        this.currentHandler = handler;
    }
    public <Q, K> Pipeline<Q, I, K> addHandler(Handler<Q, O, K> handler) {
        return new Pipeline<>(input -> handler.process(this.currentHandler.process(input)));
    }

    public void execute() throws PipelineException, NoDataToReceiveException {
        currentHandler.process(null);
    }
}
