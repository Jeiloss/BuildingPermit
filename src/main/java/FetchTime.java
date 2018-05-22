//import org.apache.commons.net.ntp.NTPUDPClient;
//
//import java.net.SocketException;
//
//public class FetchTime {
//    public static void main(String[] args) throws SocketException {
//
//        SntpClient client = new SntpClient();
//        if (client.requestTime("time.google.com")) {
//            long now = client.getNtpTime() + SystemClock.elapsedRealtime() -
//                    client.getNtpTimeReference();
//            Date current = new Date(now);
//            Log.i("NTP tag", current.toString());
//        }
//
//    }
//}
