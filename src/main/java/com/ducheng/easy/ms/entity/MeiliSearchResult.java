package com.ducheng.easy.ms.entity;

import java.io.Serializable;
import java.util.List;



public class MeiliSearchResult<T> implements Serializable {

   private List<T> hits;

   private Integer limit;

   private Integer offset;

   private Integer estimatedTotalHits;


   public List<T> getHits() {
      return hits;
   }

   public void setHits(List<T> hits) {
      this.hits = hits;
   }

   public Integer getLimit() {
      return limit;
   }

   public void setLimit(Integer limit) {
      this.limit = limit;
   }

   public Integer getOffset() {
      return offset;
   }

   public void setOffset(Integer offset) {
      this.offset = offset;
   }

   public Integer getEstimatedTotalHits() {
      return estimatedTotalHits;
   }

   public void setEstimatedTotalHits(Integer estimatedTotalHits) {
      this.estimatedTotalHits = estimatedTotalHits;
   }

   public MeiliSearchResult() {
   }

   public MeiliSearchResult(List<T> hits, Integer limit, Integer offset, Integer estimatedTotalHits) {
      this.hits = hits;
      this.limit = limit;
      this.offset = offset;
      this.estimatedTotalHits = estimatedTotalHits;
   }
}
