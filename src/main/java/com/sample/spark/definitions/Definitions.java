package com.sample.spark.definitions;

/**
 * @author Steve Johnson <sjohnson@12digitmedia.com> on 5/3/16
 */
public interface Definitions {

    String TDX_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    String DEFAULT_TZ = "UTC";
    String FIELD_SEP = "\t";
    String DEFAULT_CHARSET = "UTF-8";

    String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    String DATE_FORMAT_HIVE = "yyyy-MM-dd HH:mm:ss.SSS";
    String MONTH_FORMAT = "yyyyMM0100";
    String DAY_FORMAT = "yyyyMMdd00";
    String HOUR_FORMAT = "yyyyMMddHH";
    String QTR_FORMAT = "yyyyMM0100";

    long MILLIS_PER_DAY = 86400000L;

    String PROPERTY_FILE_NAME_SYSTEM_PROP_NAME = "app.properties.file";

    interface Properties {
        String LOCAL_MODE = "local.mode";
        String LOCAL_NUM_THREADS = "local.num.threads";
        String LOCAL_APP_NAME = "local.app.name";
        String LOCAL_HIVE_WAREHOUSE = "local.hive.warehouse";
        String LOCAL_HIVE_JDBC = "local.hive.jdbc";
        String LOCAL_METASTORE_URIS = "local.metastore.uris";
    }

    interface PropertyDefaults {
        String LOCAL_MODE = "false";
        String LOCAL_NUM_THREADS = "1";
        String LOCAL_HIVE_WAREHOUSE = "/user/hive/warehouse";
        String LOCAL_HIVE_JDBC = "jdbc:derby:;databaseName=/opt/hive/metastore_db;create=false";
        String LOCAL_METASTORE_URIS = "";
    }

    interface TaxoRecordFieldPos {
        int UPDATE_DATE = 0;
        int TDX_NODE_ID = 1;
        int RETAILER_NODE_ID = 2;
        int RETAILER_ID = 3;
        int NODE_NAME = 4;
        int APN_SEGEMENT_ID = 5;
        int TDX_PARENT_NODE_ID = 6;
        int RETAILER_PARENT_NODE_ID = 7;
        int ENTITY_TYPE = 8;
        int TDX_NODE_ID_CAT_PATH = 9;
        int RETAILER_NODE_ID_CAT_PATH = 10;
        int NODE_NAME_CAT_PATH = 11;
        int ENTITY_STATUS = 12;
        int CHILD_COUNT = 13;
        int TAXONOMY_ID = 14;

    }

    interface ApnCampaigns {
        int TDM_SEGMENT_ID = 0;
        int APN_CAMPAIGN_ID = 1;
        int AD_GROUP_ID = 2;
        int TDM_CAMPAIGN_ID = 3;
        int ACCOUNT_ID = 4;
        int RETAILER_ID = 5;
        int RETAILER_KEY = 6;
        int TEST_RETAILER_KEY = 7;
        int FLOOR_CPM = 8;
        int MARGIN_PCT = 9;


    }

    interface AppNexusStdFeedPos {
        int AUCTION_ID_64 = 0;
        int DATE = 1;
        int EVENT_TYPE = 7;
        int IMP_TYPE = 8;
        int MEDIA_COST_DOLLARS_CPM = 10;
        int BUYER_BID = 13;
        int USER_ID_64 = 19;
        int AVDERTISER_ID = 44;
        int ADVERTISER_FREQUENCY = 46;
        int INSERTION_ORDER_ID = 48;
        int CAMPAIGN_GROUP_ID = 49;
        int CAMPAIGN_ID = 50;
        int CREATIVE_ID = 51;
        int LEAF_NAME = 111;

        int IP_ADDRESS = 20;
        int SITE_DOMAIN =  31;
        int FOLD_POSITION = 6;
        int COUNTRY = 22;
        int REGION = 23;
        int CITY = 97;
        int POSTAL_CODE = 114;
        int BROWSER = 25;
        int OPERATING_SYSTEM = 24;
        int DEVICE_TYPE = 113;
        int CARRIER_ID = 85;
        int DEVICE_MODEL_ID = 84;
        int MOBILE_APP_INSTANCE_ID = 98;
        int DEAL_ID = 86;
        int DEAL_TYPE = 101;
        int DEVICE_UNIQUE_ID = 83;
        int VIEW_RESULT = 87;
        int VIEW_NON_MEASURABLE_REASON = 93;
        int TAG_ID = 32;
        int SUPPLY_TYPE = 89;
        int PUBLISHER_ID = 29;
        int CREATIVE_WIDTH = 3;
        int CREATIVE_HEIGHT = 4;






    }
    interface AppnexusSegmentFeedPos {
        int DATE = 0;
        int USER_ID_64 = 1;
        int MEMBER_ID = 2;
        int SEGMENT_ID = 3;
        int IS_DAILY_UNIQUE = 4;
        int IS_MONTHLY_UNIQUE = 5;
        int VALUE = 6;
    }
    interface AppNexusVideoFeedPos {

        int DATE = 0;
        int AUCTION_ID_64 = 1;
        int ADVERTISER_ID = 4;
        int INSERTION_ORDER_ID = 8;
        int CAMPAIGN_GROUP_ID = 9;
        int CAMPAIGN_ID = 10;
        int CREATIVE_ID = 11;

        int VIDEO_AUCTION = 19;
        int VIDEO_STARTED = 20;
        int VIDEO_WAS_SKIPPED = 21;
        int VIDEO_HAD_ERROR = 22;
        int VIDEO_HIT_25_PCT = 23;
        int VIDEO_HIT_50_PCT = 24;
        int VIDEO_HIT_75_PCT = 25;
        int VIDEO_COMPLETED = 26;
        int IMP_TYPE = 27;
        int MEDIA_COST_DOLLARS_CPM = 32;
    }

    interface VisitAttribution {
        int VISIST_TIMESTAMP = 0;
        int IMP_TIMESTAMP = 1;
        int CAMPAIGN_ID = 8;
        int LEAF_NAME = 14;
    }

    interface LeafInfo {
        int ADGROUP_ID = 0;
        int TDM_CAMPAIGN_ID = 1;
        int APN_CAMPAIGN_ID = 2;
        int TDM_MODEL_ID = 3;
        int APN_MODEL_ID = 4;
        int LEAF_NAME = 5;
        int TDM_SEGMENT_ID = 6;
        int APN_SEGMENT_ID = 7;
    }

    interface TdxSiteVisit {
        int TIMESTAMP = 0;
        int TDM_RETAILER_ID = 1;
        int RETAILER_USER_ID = 2;
        int APPNEXUS_USER_ID = 3;
        int TDM_USER_ID = 4;
        int OPT_OUT = 5;
        int FUNNEL_STAGE = 6;
        int DEPARTMENT = 7;
        int CATEGORY = 8;
        int SUB_CATEGORY = 9;
        int BRAND = 10;
        int PRODUCT = 11;
        int USER_AGENT = 12;
        int REFERRER_URL = 13;
        int IP_ADDRESS = 14;
        int SERVER_ID = 15;
        int EVENT_ID = 16;
        int SESSION_ID = 17;
        int SEGMENTS = 18;
    }

    interface TdxPurchase {

        @SuppressWarnings("unused")
        String PRODUCT_SPLIT_PATTERN = ",\"|,]";

        int TIMESTAMP = 0;
        int PARTNER_ID = 1;
        int PARTNER_USER_ID = 2;
        int APPNEXUS_USER_ID = 3;
        int TDM_USER_ID = 4;
        int TDM_OPTED_OUT = 5;
        int TRANSACTION_ID = 6;
        int SUB_TOTAL = 7;
        int DISCOUNT = 8;
        int PRODUCTS = 9;
        int USER_AGENT = 10;
        int REFERER_URL = 11;
        int IP_ADDRESS = 12;
        int SERVER_ID = 13;
        int EVENT_ID = 14;

        interface Product {
            int PRODUCT_ID = 0;
            int AMOUNT = 1;
            int QUANTITY = 2;
            int DISCOUNT = 3;

            String DELIMITER = ",";
        }

    }

    interface NeimanPurchase {

        interface Header {
            int SHIPPING_CUSTOMER = 1;
            int SALES_AUDIT_ORDER_ID = 4;
            int TRANSACTION_DATE = 9;
            int TRANSACTION_TYPE = 10;
            int RETURN_FLAG = 11;
            int GROSS_SALES = 17;
            int TOTAL_DISCOUNTS = 19;
            int NET_SALES = 20;
            int WEB_ORDER_ID = 7;
        }

        interface Detail {
            int SALES_AUDIT_ORDER_ID = 0;
            int PRODUCT_ID = 2;
            int BRAND_NAME = 5;
            int LINE_ITEM_ID = 6;
            int LINE_ITEM_RETAIL_SALE = 7;
            int LINE_ITEM_PROMOTIONAL_SALE = 8;
            int LINE_ITEM_CLEARANCE_SALE = 9;
            int LINE_ITEM_QTY = 13;
            int LINE_ITEM_STATUS = 14;
        }
    }

    interface TaxoSegmentKeyword {

        int TAXO_NODE_ID = 0;
        int KEY = 1;
        int TAXONOMY_ID = 2;
        int RETAILER_ID = 3;

    }

    interface TaxoSegmentProductFeed {

        int PRODUCT_NODE_ID = 0;
        int TDM_BRAND_NODE_ID = 1;
        int TDM_CATEGORY_NODE_ID = 2;
        int PRODUCT_ID = 3;
        int TAXONOMY_ID = 4;
        int RETAILER_ID = 5;
        int PRODUCT_NAME = 6;

    }

    interface TaxoAdSegmentDbColumn {

        String TAXONOMY_NODE_ID = "taxonomy_node_id";
        String AD_EXCHANGE = "ad_exchange";
        String SEGMENT_TYPE = "segment_type";
        String SEGMENT_ID = "segment_id";
        String RETAILER_ID = "retailer_id";
        String TAXONOMY_ID = "taxonomy_id";

        String ADVERTISER_ID = "advertiser_id";
        String ADX_SEGMENT_TYPE = "adx_segment_type";
        String CREATED_DATE = "creted_date";


    }

    interface TdxBrandFeed {

        int BRAND_NODE_ID = 0;
        int BRAND_NAME = 1;

    }

    interface FbCampaigns {
        int FB_CAMPAIGN_ID = 0;
        int AD_GROUP_ID = 1;
        int TDM_CAMPAIGN_ID = 2;
        int ACCOUNT_ID = 3;
        int RETAILER_ID = 4;
        int RETAILER_KEY = 5;
        int TEST_RETAILER_KEY = 6;
        int FLOOR_CPM = 7;
        int MARGIN_PCT = 8;
    }

    interface FbCampaignMapInfo {
        int TDM_ADGROUP_ID = 0;
        int FB_ADSET_ID = 1;
        int TDM_CAMPAIGN_ID = 2;
        int FB_CAMPAIGN_ID = 3;
    }

    interface FacebookStdFeedPos {
        int DATE = 0;
        int RETAILER_ID = 1;
        int FB_ACCOUNT_ID = 2;
        int FB_ADSET_ID = 3;
        int FB_ADSET_NAME = 4;
        int FB_CAMPAIGN_ID = 5;
        int CAMPAIGN_NAME = 6;
        int IMPRESSION_COUNT = 7;
        int CLICK_COUNT = 8;
        int SPEND = 9;
    }


    interface TaxoRecordV2FieldPos {

        int NODE_ID = 0;
        int NODE_NAME = 1;
        int PARENT_SEGMENT_NODE_ID = 2;
        int PARENT_SEGMENT_NODE_NAME = 3;
        int BRAND_NODE_ID = 4;
        int BRAND_NODE_NAME = 5;
        int BC_IDS = 6;
        int BC_NAMES = 7;
        int CHILD_COUNT = 8;
        int TAXONOMY_ID = 9;
        int ROW_TYPE = 10;
    }

    interface TaxoMatchKey {

        int NODE_ID = 0;
        int BRAND_NODE_ID = 1;
        int MATCH_KEY = 2;
        int TAXONOMY_ID = 3;
        int MATCH_KEY_TYPE = 4;


    }
}
