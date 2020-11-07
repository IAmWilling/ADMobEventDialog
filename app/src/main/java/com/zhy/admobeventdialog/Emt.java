package com.zhy.admobeventdialog;

import com.admob.admobevwindow.AgentEv;

public class Emt {
    /**
     * 搜索页-直接输入文字搜
     */
    @AgentEv(act = "MainActivity",desc = "搜索页-直接输入文字搜")
    public static final String SEARCH_PAGE_USE_WORDS = "page_search_use_words";
    /**
     * 搜索页-点击联想词搜
     */
    public static final String SEARCH_PAGE_USE_WORDS_LIST = "page_search_use_words_list";

    /**
     * 搜索页-点击按菜名搜
     */
    public static final String SEARCH_PAGE_USE_NAME_LIST = "page_search_use_name";
    /**
     * 搜索页-点击按食材搜
     */
    @AgentEv(act = "MainActivity",desc = "搜索页-点击按食材搜")
    public static final String SEARCH_PAGE_USE_MATREAL_LIST = "page_search_use_materil";
    /**
     * 搜索结果页点击菜谱数
     */
    public static final String SEARCH_PAGE_RESULT_RECIPE_CLICK = "page_search_result_list_click";


    /**
     * 详情页-点击右上角收藏按钮
     */
    public static final String DETAIL_PAGE_COLLECT_CLICK = "page_detail_collect_click";
    /**
     * 详情页-点击右上角菜篮子按钮
     */
    @AgentEv(act = "MainActivity",desc = "详情页-点击右上角菜篮子按钮")
    public static final String DETAIL_PAGE_BASKET_CLICK = "page_detail_basket_click";

    /**
     * 详情页-点击菜谱图片-进入烹饪模式
     */
    @AgentEv(act = "MainActivity",desc = "详情页-点击菜谱图片-进入烹饪模式")
    public static final String DETAIL_PAGE_GO_COOK_PAGE = "page_detail_go_cook_page";
    /**
     * 详情页-点击积分解锁按钮
     */
    public static final String DETAIL_PAGE_POINTS_UNLOCK_CLICK = "page_detail_unlock_recipe_click";

}
