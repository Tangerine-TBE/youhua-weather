package com.example.module_ad.bean

import com.example.module_ad.base.IBaseAdBean
import com.example.module_ad.base.IBaseXXBean
import kotlin.random.Random

class AdBean {
    /**
     * code : 0
     * message : 请求成功
     * data : {"start_page":{"spread_screen":{"status":true,"ad_origin":"gdt_toutiao","times":1,"change_times":300,"ad_percent":"0_100"}},"home_page":{"native_screen":{"status":true,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"}},"city_manager_page":{"native_screen":{"status":true,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"},"banner_screen":{"status":true,"ad_origin":"gdt_toutiao","times":200,"change_times":300,"ad_percent":"0_100"}},"airquality_page":{"native_screen":{"status":true,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"},"banner_screen":{"status":true,"ad_origin":"gdt_toutiao","times":200,"change_times":300,"ad_percent":"0_100"}},"temperature_page":{"native_screen":{"status":true,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"},"banner_screen":{"status":true,"ad_origin":"gdt_toutiao","times":200,"change_times":300,"ad_percent":"0_100"}},"housingloan_page":{"native_screen":{"status":true,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"},"banner_screen":{"status":true,"ad_origin":"gdt_toutiao","times":200,"change_times":300,"ad_percent":"0_100"}},"setting_page":{"incentive_video":{"status":true,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"},"native_screen":{"status":true,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"},"banner_screen":{"status":true,"ad_origin":"gdt_toutiao","times":200,"change_times":300,"ad_percent":"0_100"}},"exit_page":{"native_screen":{"status":true,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"}},"Advertisement":{"kTouTiaoAppKey":"5091408","kTouTiaoKaiPing":"887356266","kTouTiaoBannerKey":"945346625","kTouTiaoChaPingKey":"945346711","kTouTiaoJiLiKey":"945349182","kTouTiaoSeniorKey":"945348027","kGDTMobSDKAppKey":"1110657167","kGDTMobSDKChaPingKey":"3061624292821499","kGDTMobSDKKaiPingKey":"7011221282501342","kGDTMobSDKBannerKey":"8001721242218340","kGDTMobSDKNativeKey":"1041424292506364","kGDTMobSDKJiLiKey":"7021827242606462"}}
     */
    var code = 0
    var message: String? = null
    var data: DataBean? = null

    class DataBean {
        /**
         * start_page : {"spread_screen":{"status":true,"ad_origin":"gdt_toutiao","times":1,"change_times":300,"ad_percent":"0_100"}}
         * home_page : {"native_screen":{"status":true,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"}}
         * city_manager_page : {"native_screen":{"status":true,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"},"banner_screen":{"status":true,"ad_origin":"gdt_toutiao","times":200,"change_times":300,"ad_percent":"0_100"}}
         * airquality_page : {"native_screen":{"status":true,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"},"banner_screen":{"status":true,"ad_origin":"gdt_toutiao","times":200,"change_times":300,"ad_percent":"0_100"}}
         * temperature_page : {"native_screen":{"status":true,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"},"banner_screen":{"status":true,"ad_origin":"gdt_toutiao","times":200,"change_times":300,"ad_percent":"0_100"}}
         * housingloan_page : {"native_screen":{"status":true,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"},"banner_screen":{"status":true,"ad_origin":"gdt_toutiao","times":200,"change_times":300,"ad_percent":"0_100"}}
         * setting_page : {"incentive_video":{"status":true,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"},"native_screen":{"status":true,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"},"banner_screen":{"status":true,"ad_origin":"gdt_toutiao","times":200,"change_times":300,"ad_percent":"0_100"}}
         * exit_page : {"native_screen":{"status":true,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"}}
         * Advertisement : {"kTouTiaoAppKey":"5091408","kTouTiaoKaiPing":"887356266","kTouTiaoBannerKey":"945346625","kTouTiaoChaPingKey":"945346711","kTouTiaoJiLiKey":"945349182","kTouTiaoSeniorKey":"945348027","kGDTMobSDKAppKey":"1110657167","kGDTMobSDKChaPingKey":"3061624292821499","kGDTMobSDKKaiPingKey":"7011221282501342","kGDTMobSDKBannerKey":"8001721242218340","kGDTMobSDKNativeKey":"1041424292506364","kGDTMobSDKJiLiKey":"7021827242606462"}
         */
        var start_page: StartPageBean? = null
        var home_page: HomePageBean? = null
        var city_manager_page: CityManagerPageBean? = null
        var airquality_page: AirqualityPageBean? = null
        var temperature_page: TemperaturePageBean? = null
        var housingloan_page: HousingloanPageBean? = null
        var setting_page: SettingPageBean? = null
        var exit_page: ExitPageBean? = null
        var advertisement: AdvertisementBean? = null

        class StartPageBean {
            /**
             * spread_screen : {"status":true,"ad_origin":"gdt_toutiao","times":1,"change_times":300,"ad_percent":"0_100"}
             */
            var spread_screen: SpreadScreenBean? = null

            class SpreadScreenBean {
                /**
                 * status : true
                 * ad_origin : gdt_toutiao
                 * times : 1
                 * change_times : 300
                 * ad_percent : 0_100
                 */
                var isStatus = false
                var ad_origin: String? = null
                var times = 0
                var change_times = 0
                var ad_percent: String? = null
                fun randomFirstAd():Boolean{
                    val f=ad_percent?.split("_")?.map {
                        it.toIntOrNull()?:0
                    }?: listOf(50,50)
                    val random=Random.nextInt(f.sum())+1
                    return random<=(f.firstOrNull()?:0)
                }
            }
        }

        class HomePageBean {
            /**
             * native_screen : {"status":true,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"}
             */
            var native_screen: NativeScreenBean? = null

            class NativeScreenBean {
                /**
                 * status : true
                 * ad_origin : gdt_toutiao
                 * times : 5
                 * change_times : 10
                 * ad_percent : 0_100
                 */
                var isStatus = false
                var ad_origin: String? = null
                var times = 0
                var change_times = 0
                var ad_percent: String? = null
                fun randomFirstAd():Boolean{
                    val f=ad_percent?.split("_")?.map {
                        it.toIntOrNull()?:0
                    }?: listOf(50,50)
                    val random=Random.nextInt(f.sum())+1
                    return random<=(f.firstOrNull()?:0)
                }
            }
        }

        class CityManagerPageBean : IBaseAdBean {
            /**
             * native_screen : {"status":true,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"}
             * banner_screen : {"status":true,"ad_origin":"gdt_toutiao","times":200,"change_times":300,"ad_percent":"0_100"}
             */
            var native_screen: NativeScreenBeanX? = null
            var banner_screen: BannerScreenBean? = null
            override fun getBaseBanner_screen(): IBaseXXBean {
                return banner_screen!!
            }

            override fun getBaseNative_screen(): IBaseXXBean {
                return native_screen!!
            }

            class NativeScreenBeanX : IBaseXXBean {
                /**
                 * status : true
                 * ad_origin : gdt_toutiao
                 * times : 5
                 * change_times : 10
                 * ad_percent : 0_100
                 */
                var isStatus = false
                var ad_origin: String? = null
                var times = 0
                var change_times = 0
                var ad_percent: String? = null
                override fun getBaseAd_percent(): String {
                    return ad_percent!!
                }

                override fun isBaseStatus(): Boolean {
                    return isStatus
                }
            }

            class BannerScreenBean : IBaseXXBean {
                /**
                 * status : true
                 * ad_origin : gdt_toutiao
                 * times : 200
                 * change_times : 300
                 * ad_percent : 0_100
                 */
                var isStatus = false
                var ad_origin: String? = null
                var times = 0
                var change_times = 0
                var ad_percent: String? = null
                override fun getBaseAd_percent(): String {
                    return ad_percent!!
                }

                override fun isBaseStatus(): Boolean {
                    return isStatus
                }
            }
        }

        class AirqualityPageBean : IBaseAdBean {
            /**
             * native_screen : {"status":true,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"}
             * banner_screen : {"status":true,"ad_origin":"gdt_toutiao","times":200,"change_times":300,"ad_percent":"0_100"}
             */
            var native_screen: NativeScreenBeanXX? = null
            var banner_screen: BannerScreenBeanX? = null
            override fun getBaseBanner_screen(): IBaseXXBean {
                return banner_screen!!
            }

            override fun getBaseNative_screen(): IBaseXXBean {
                return native_screen!!
            }

            class NativeScreenBeanXX : IBaseXXBean {
                /**
                 * status : true
                 * ad_origin : gdt_toutiao
                 * times : 5
                 * change_times : 10
                 * ad_percent : 0_100
                 */
                var isStatus = false
                var ad_origin: String? = null
                var times = 0
                var change_times = 0
                var ad_percent: String? = null
                override fun getBaseAd_percent(): String {
                    return ad_percent!!
                }

                override fun isBaseStatus(): Boolean {
                    return isStatus
                }
            }

            class BannerScreenBeanX : IBaseXXBean {
                /**
                 * status : true
                 * ad_origin : gdt_toutiao
                 * times : 200
                 * change_times : 300
                 * ad_percent : 0_100
                 */
                var isStatus = false
                var ad_origin: String? = null
                var times = 0
                var change_times = 0
                var ad_percent: String? = null
                override fun getBaseAd_percent(): String {
                    return ad_percent!!
                }

                override fun isBaseStatus(): Boolean {
                    return isStatus
                }
            }
        }

        class TemperaturePageBean : IBaseAdBean {
            /**
             * native_screen : {"status":true,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"}
             * banner_screen : {"status":true,"ad_origin":"gdt_toutiao","times":200,"change_times":300,"ad_percent":"0_100"}
             */
            var native_screen: NativeScreenBeanXXX? = null
            var banner_screen: BannerScreenBeanXX? = null
            override fun getBaseBanner_screen(): IBaseXXBean {
                return banner_screen!!
            }

            override fun getBaseNative_screen(): IBaseXXBean {
                return native_screen!!
            }

            class NativeScreenBeanXXX : IBaseXXBean {
                /**
                 * status : true
                 * ad_origin : gdt_toutiao
                 * times : 5
                 * change_times : 10
                 * ad_percent : 0_100
                 */
                var isStatus = false
                var ad_origin: String? = null
                var times = 0
                var change_times = 0
                var ad_percent: String? = null
                override fun getBaseAd_percent(): String {
                    return ad_percent!!
                }

                override fun isBaseStatus(): Boolean {
                    return isStatus
                }
            }

            class BannerScreenBeanXX : IBaseXXBean {
                /**
                 * status : true
                 * ad_origin : gdt_toutiao
                 * times : 200
                 * change_times : 300
                 * ad_percent : 0_100
                 */
                var isStatus = false
                var ad_origin: String? = null
                var times = 0
                var change_times = 0
                var ad_percent: String? = null
                override fun getBaseAd_percent(): String {
                    return ad_percent!!
                }

                override fun isBaseStatus(): Boolean {
                    return isStatus
                }
            }
        }

        class HousingloanPageBean : IBaseAdBean {
            /**
             * native_screen : {"status":true,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"}
             * banner_screen : {"status":true,"ad_origin":"gdt_toutiao","times":200,"change_times":300,"ad_percent":"0_100"}
             */
            var native_screen: NativeScreenBeanXXXX? = null
            var banner_screen: BannerScreenBeanXXX? = null
            override fun getBaseBanner_screen(): IBaseXXBean {
                return banner_screen!!
            }

            override fun getBaseNative_screen(): IBaseXXBean {
                return native_screen!!
            }

            class NativeScreenBeanXXXX : IBaseXXBean {
                /**
                 * status : true
                 * ad_origin : gdt_toutiao
                 * times : 5
                 * change_times : 10
                 * ad_percent : 0_100
                 */
                var isStatus = false
                var ad_origin: String? = null
                var times = 0
                var change_times = 0
                var ad_percent: String? = null
                override fun getBaseAd_percent(): String {
                    return ad_percent!!
                }

                override fun isBaseStatus(): Boolean {
                    return isStatus
                }
            }

            class BannerScreenBeanXXX : IBaseXXBean {
                /**
                 * status : true
                 * ad_origin : gdt_toutiao
                 * times : 200
                 * change_times : 300
                 * ad_percent : 0_100
                 */
                var isStatus = false
                var ad_origin: String? = null
                var times = 0
                var change_times = 0
                var ad_percent: String? = null
                override fun getBaseAd_percent(): String {
                    return ad_percent!!
                }

                override fun isBaseStatus(): Boolean {
                    return isStatus
                }
            }
        }

        class SettingPageBean : IBaseAdBean {
            /**
             * incentive_video : {"status":true,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"}
             * native_screen : {"status":true,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"}
             * banner_screen : {"status":true,"ad_origin":"gdt_toutiao","times":200,"change_times":300,"ad_percent":"0_100"}
             */
            var incentive_video: IncentiveVideoBean? = null
            var native_screen: NativeScreenBeanXXXXX? = null
            var banner_screen: BannerScreenBeanXXXX? = null
            override fun getBaseBanner_screen(): IBaseXXBean {
                return banner_screen!!
            }

            override fun getBaseNative_screen(): IBaseXXBean {
                return native_screen!!
            }

            class IncentiveVideoBean {
                /**
                 * status : true
                 * ad_origin : gdt_toutiao
                 * times : 5
                 * change_times : 10
                 * ad_percent : 0_100
                 */
                var isStatus = false
                var ad_origin: String? = null
                var times = 0
                var change_times = 0
                var ad_percent: String? = null
            }

            class NativeScreenBeanXXXXX : IBaseXXBean {
                /**
                 * status : true
                 * ad_origin : gdt_toutiao
                 * times : 5
                 * change_times : 10
                 * ad_percent : 0_100
                 */
                var isStatus = false
                var ad_origin: String? = null
                var times = 0
                var change_times = 0
                var ad_percent: String? = null
                override fun getBaseAd_percent(): String {
                    return ad_percent!!
                }

                override fun isBaseStatus(): Boolean {
                    return isStatus
                }
            }

            class BannerScreenBeanXXXX : IBaseXXBean {
                /**
                 * status : true
                 * ad_origin : gdt_toutiao
                 * times : 200
                 * change_times : 300
                 * ad_percent : 0_100
                 */
                var isStatus = false
                var ad_origin: String? = null
                var times = 0
                var change_times = 0
                var ad_percent: String? = null
                override fun getBaseAd_percent(): String {
                    return ad_percent!!
                }

                override fun isBaseStatus(): Boolean {
                    return isStatus
                }
            }
        }

        class ExitPageBean {
            /**
             * native_screen : {"status":true,"ad_origin":"gdt_toutiao","times":5,"change_times":10,"ad_percent":"0_100"}
             */
            var native_screen: NativeScreenBeanXXXXXX? = null

            class NativeScreenBeanXXXXXX {
                /**
                 * status : true
                 * ad_origin : gdt_toutiao
                 * times : 5
                 * change_times : 10
                 * ad_percent : 0_100
                 */
                var isStatus = false
                var ad_origin: String? = null
                var times = 0
                var change_times = 0
                var ad_percent: String? = null
            }
        }

        class AdvertisementBean {
            /**
             * kTouTiaoAppKey : 5091408
             * kTouTiaoKaiPing : 887356266
             * kTouTiaoBannerKey : 945346625
             * kTouTiaoChaPingKey : 945346711
             * kTouTiaoJiLiKey : 945349182
             * kTouTiaoSeniorKey : 945348027
             * kGDTMobSDKAppKey : 1110657167
             * kGDTMobSDKChaPingKey : 3061624292821499
             * kGDTMobSDKKaiPingKey : 7011221282501342
             * kGDTMobSDKBannerKey : 8001721242218340
             * kGDTMobSDKNativeKey : 1041424292506364
             * kGDTMobSDKJiLiKey : 7021827242606462
             */
            var kTouTiaoAppKey: String? = null
            var kTouTiaoKaiPing: String? = null
            var kTouTiaoBannerKey: String? = null
            var kTouTiaoChaPingKey: String? = null
            var kTouTiaoJiLiKey: String? = null
            var kTouTiaoSeniorKey: String? = null

            var kWaiAppKey: String? = null
            var kWaiKaiPing: String? = null
            var kWaiBannerKey: String? = null
            var kWaiChaPingKey: String? = null
            var kWaiJiLiKey: String? = null
            var kWaiSeniorKey: String? = null

            var kGDTMobSDKAppKey: String? = null
            var kGDTMobSDKChaPingKey: String? = null
            var kGDTMobSDKKaiPingKey: String? = null
            var kGDTMobSDKBannerKey: String? = null
            var kGDTMobSDKNativeKey: String? = null
            var kGDTMobSDKJiLiKey: String? = null
        }
    }
}