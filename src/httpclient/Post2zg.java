package httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Post2zg {

	public static void main(String[] args) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
        	String url="http://10.10.142.235:18083/com.cmcc.sh.boss.interfaces.forcrm";
            HttpPost httpPost = new HttpPost(url);
            //[{'Content-Type','application/json; charset=UTF-8'},{'Connection','keep-alive'}]
            httpPost.addHeader("Content-Type", "application/json; charset=UTF-8");
            httpPost.addHeader("Connection", "keep-alive");
            
            String postJson = "{\"sOper\":{\"org_id\":0\"op_id\":9\"busi_code\":7360\"so_mode\":2\"so_nbr\":\"20141016154549456337\"}\"req\":{\"OrderID\":\"BOSS1079917471157\"}}";
//            String postJson="{\"sOper\":{\"org_id\":0,\"op_id\":100000678920,\"so_date\":\"2014-10-16 15:45:49\",\"busi_code\":7191,\"step_id\":1,\"so_mode\":100,\"so_nbr\":\"20141016154549456337\"},\"req\":{\"book_item_id_out\":5553651,\"fund_type\":1,\"allot_busi_code\":202610001,\"allot_bcycle_count\":12,\"allot_scale\":\"0|0|0|0|0|0|0|0|0|0|0|0|\",\"allot_amount\":100000,\"allot_mode_value\":\"1\",\"book_item_id_in\":5023641,\"outer_promo_id\":25492,\"promo_name\":\"test11\"}}";
            StringEntity jsonEntity = new StringEntity(postJson,"UTF-8");
            httpPost.setEntity(jsonEntity);
            
            CloseableHttpResponse response2 = httpclient.execute(httpPost);

            try {
                System.out.println(response2.getStatusLine());
                HttpEntity entity2 = response2.getEntity();
                // do something useful with the response body
                // and ensure it is fully consumed
//                EntityUtils.consume(entity2);
                String res = EntityUtils.toString(entity2);
                System.out.println(res);
                
            } finally {
                response2.close();
            }
        } finally {
            httpclient.close();
        }
    }

}
