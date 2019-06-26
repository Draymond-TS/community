package life.draymond.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO<T> {
    private List<T> data;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private  Integer page;
    private List<Integer> pages=new ArrayList<Integer>();
    private Integer totalPage;

    public void setPagination(Integer totalCount, Integer page, Integer size) {

        //总页数
        if(totalCount%size==0){
            this.totalPage=totalCount/size;
        }else{
            this.totalPage=totalCount/size +1;
        }


        if(page<1){
            this.page=1;
        }else if(page>totalPage){
            this.page=totalPage;
        }else {
            this.page=page;
        }


        //设置分页显示数  由数组代表
        pages.add(page);
        for(int i=1;i<=3;i++){
            if(page-i>0){
                pages.add(0,page -i);
            }
            if(page+i<=totalPage){
                pages.add(page+i);
            }
        }



        //是否显示上一页
        if(page==1){
            showPrevious=false;
        }else{
            showPrevious=true;
        }
        //是否显示下一页
        if(page==totalPage){
            showNext=false;
        }else{
            showNext=true;
        }
        //是否显示第一页
        if(pages.contains(1)){
            showFirstPage=false;
        }else{
            showFirstPage=true;
        }
        //是否显示最后一页
        if(pages.contains(totalPage)){
            showEndPage=false;
        }else{
            showEndPage=true;
        }
    }
}
