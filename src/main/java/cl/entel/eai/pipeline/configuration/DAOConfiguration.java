package cl.entel.eai.pipeline.configuration;

import cl.entel.eai.exception.PipelineException;

public abstract class DAOConfiguration<D> implements Configuration{
    protected D dao;
    protected int chunkSize;
    protected long offset;

    public DAOConfiguration() { }

    public void init() throws PipelineException {};

    public int getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public void incrementOffset(long offset) {
        this.offset += offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public long getOffset() {
        return offset;
    }

    public D getDao() {
        return dao;
    }

    public void setDao(D dao) {
        this.dao = dao;
    }
}
