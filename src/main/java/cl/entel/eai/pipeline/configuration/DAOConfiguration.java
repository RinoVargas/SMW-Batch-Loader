package cl.entel.eai.pipeline.configuration;

public abstract class DAOConfiguration<D> {
    private D dao;
    private int chunkSize;
    private long offset;
    private long totalRecords;

    public DAOConfiguration() { }

    public int getChunkSize() {
        return chunkSize;
    }

    public void setChunkSize(int chunkSize) {
        this.chunkSize = chunkSize;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public void setTotalRecords(long totalRecords) {
        this.totalRecords = totalRecords;
    }

    public long getOffset() {
        return offset;
    }

    public long getTotalRecords() {
        return totalRecords;
    }


    public D getDao() {
        return dao;
    }

    public void setDao(D dao) {
        this.dao = dao;
    }
}
