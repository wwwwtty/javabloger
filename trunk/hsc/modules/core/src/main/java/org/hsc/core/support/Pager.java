package org.hsc.core.support;

import java.util.Collection;
import java.util.List;

/**
 * 分页操作类,该类来自于互联网,做了部分修改,以适应需求
 * @author heshencao@163.com
 */
public final class Pager<T> {
        /**总行数*/
        private int totalRows; 
        /**每页显示的行数*/
        private int pageSize ; 
        /**当前页号*/
        private int currentPage;  
        /**总页数*/
        private int totalPages; 
        /**当前页在数据库中的起始行*/
        private int startRow; 
        
        private Collection<T> data;
        
        private Float exemptMoney;
        
        private Float coupon;
        
        private Float receivedMoney;
        
        private Float receivableMoney;
        
        
        public Float getReceivableMoney() {
			return receivableMoney;
		}

		public void setReceivableMoney(Float receivableMoney) {
			this.receivableMoney = receivableMoney;
		}

		public Float getExemptMoney() {
			return exemptMoney;
		}

		public void setExemptMoney(Float exemptMoney) {
			this.exemptMoney = exemptMoney;
		}

		public Float getCoupon() {
			return coupon;
		}

		public void setCoupon(Float coupon) {
			this.coupon = coupon;
		}

		public Float getReceivedMoney() {
			return receivedMoney;
		}

		public void setReceivedMoney(Float receivedMoney) {
			this.receivedMoney = receivedMoney;
		}

		public Pager() {
                currentPage = 1;
                startRow = 0;
        }

        public void process() {
        		if(totalRows<1 || pageSize<1){
        			totalPages=1;
        			currentPage=1;
        			startRow=0;
        		}else{
	                totalPages = totalRows / pageSize;
	                int mod = totalRows % pageSize;
	                if (mod > 0) {
	                        totalPages++;
	                }
	                if (this.currentPage <= 0) {
	                        currentPage = 1;
	                }
	                startRow = (currentPage - 1) * pageSize;
        		}
        }

        public int getStartRow() {
                return startRow;
        }

        public int getTotalPages() {
                return totalPages;
        }

        public int getCurrentPage() {
                return currentPage;
        }

        public int getPageSize() {
                return pageSize;
        }

        public void setTotalRows(int totalRows) {
                this.totalRows = totalRows;
        }

        // public void setStartRow(int startRow) {
        // this.startRow = startRow;
        // }
        // public void setTotalPages(int totalPages) {
        // this.totalPages = totalPages;
        // }
        public void setCurrentPage(Integer currentPage) {
                this.currentPage =currentPage!=null?currentPage:0;
        }

        public void setPageSize(Integer pageSize) {
                this.pageSize = pageSize!=null?pageSize:0;
        }

        public int getTotalRows() {
                return totalRows;
        }

        public void first() {
                currentPage = 1;
                startRow = 0;
        }

        public void previous() {
                if (currentPage == 1) {
                        return;
                }
                currentPage--;
                startRow = (currentPage - 1) * pageSize;
        }

        public void next() {
                // System.out.print("next:");
                if (currentPage < totalPages) {
                        currentPage++;
                }
                startRow = (currentPage - 1) * pageSize;
        }

        public void last() {
                currentPage = totalPages;
                startRow = (currentPage - 1) * pageSize;
        }

        public void refresh(int _currentPage) {
                currentPage = _currentPage;
                if (currentPage > totalPages) {
                        last();
                }
        }

		public Collection<T> getData() {
			return data;
		}

		public void setData(Collection<T> data) {
			this.data = data;
		}
		
		
		public static <T> Pager<T> builder(List<T> list){
			Pager<T> pager=new Pager<T>();
			pager.setData(list);
			pager.setTotalRows(list.size());
			pager.setPageSize(list.size());
			pager.process();
			
			return pager;
		}
}
