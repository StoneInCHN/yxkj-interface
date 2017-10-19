package com.yxkj.json.base;

import java.util.List;

public class WaitSupplyList {
  
  /**
   * 待补货物总数
   */
  private Integer waitSupplySumCount;
  
  /**
   * 待补优享空间
   */
  private List<WaitSupplyScene> scenes;
  
  public WaitSupplyList(){};
  
  public WaitSupplyList(Integer waitSupplySumCount, List<WaitSupplyScene> scenes) {
    this.waitSupplySumCount = waitSupplySumCount;
    this.scenes = scenes;
  }

  public Integer getWaitSupplySumCount() {
    return waitSupplySumCount;
  }

  public void setWaitSupplySumCount(Integer waitSupplySumCount) {
    this.waitSupplySumCount = waitSupplySumCount;
  }

  public List<WaitSupplyScene> getScenes() {
    return scenes;
  }

  public void setScenes(List<WaitSupplyScene> scenes) {
    this.scenes = scenes;
  }
  
  public class WaitSupplyScene {
    
    /**
     * 待补优享空间编号
     */
    private String sceneSn;
    
    /**
     * 待补优享空间地址名称
     */
    private String sceneName;
    
    /**
     * 待补货柜组
     */
    private List<VendingContainerGroup> vendingContainerGroups;
    
    
    public WaitSupplyScene(String sceneSn, String sceneName) {
      this.sceneSn = sceneSn;
      this.sceneName = sceneName;
    }

    public String getSceneSn() {
      return sceneSn;
    }

    public void setSceneSn(String sceneSn) {
      this.sceneSn = sceneSn;
    }

    public String getSceneName() {
      return sceneName;
    }

    public void setSceneName(String sceneName) {
      this.sceneName = sceneName;
    }

    public List<VendingContainerGroup> getVendingContainerGroups() {
      return vendingContainerGroups;
    }

    public void setVendingContainerGroups(List<VendingContainerGroup> vendingContainerGroups) {
      this.vendingContainerGroups = vendingContainerGroups;
    }
    
    public class VendingContainerGroup{
      
      /**
       * 待补货柜
       */
      private List<WaitSupplyVendingContainer> vendingContainers;
      
      public List<WaitSupplyVendingContainer> getVendingContainers() {
        return vendingContainers;
      }

      public void setVendingContainers(List<WaitSupplyVendingContainer> vendingContainers) {
        this.vendingContainers = vendingContainers;
      }
      
      public class WaitSupplyVendingContainer {
        
        /**
         * 货柜id
         */
        private Long id;
        
        /**
         * 待补货柜名
         */
        private String vendingContainerName;
        
        /**
         * 货柜编号
         */
        private String cntrSn;
        
        /**
         * 是否为中控柜
         */
        private boolean central = false;
        
        /**
         * 货柜待补数量
         */
        private Integer waitSupplyCount = 0;
        
        public WaitSupplyVendingContainer() {}
        
        public WaitSupplyVendingContainer(Long id, String cntrSn) {
          this.id = id;
          this.cntrSn = cntrSn;
        }

        public Long getId() {
          return id;
        }

        public void setId(Long id) {
          this.id = id;
        }

        public String getVendingContainerName() {
          return vendingContainerName;
        }

        public void setVendingContainerName(String vendingContainerName) {
          this.vendingContainerName = vendingContainerName;
        }

        public String getCntrSn() {
          return cntrSn;
        }

        public void setCntrSn(String cntrSn) {
          this.cntrSn = cntrSn;
        }

        public boolean getCentral() {
          return central;
        }

        public void setCentral(boolean central) {
          this.central = central;
        }

        public Integer getWaitSupplyCount() {
          return waitSupplyCount;
        }

        public void setWaitSupplyCount(Integer waitSupplyCount) {
          this.waitSupplyCount = waitSupplyCount;
        }
      }
    }
  }
}





