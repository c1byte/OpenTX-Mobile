package com.choi_n_yang.opentx_mobile.opentx_mobile;

public class OpenTxGlobal {
        final int CHANNEL_MAX = 10;
        private int[] ch = new int[CHANNEL_MAX];

        public int getChannel(int index)
        {
            if( index < CHANNEL_MAX) {
                return this.ch[index];
            }else
            {
                return 0;
            }
        }
        public void setChannel(int value, int index )
        {
            if( index < CHANNEL_MAX) {
                this.ch[index] = value;
            }
        }
        private static OpenTxGlobal instance = null;

        public static synchronized OpenTxGlobal getInstance(){
            if(null == instance){
                instance = new OpenTxGlobal();
            }
            return instance;
        }

}
