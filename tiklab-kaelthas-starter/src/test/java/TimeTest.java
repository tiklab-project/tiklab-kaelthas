import com.alibaba.fastjson.JSONObject;
import io.tiklab.kaelthas.history.entity.HistoryEntity;

import java.lang.reflect.Field;
import java.util.List;
import java.util.StringJoiner;

public class TimeTest {
    public static void main(String[] args) {
        String tableName = "your_table_name";

        StringBuilder sqlBuilder = new StringBuilder();

        String hisStr = "[{\"monitorName\":\"cpu处于空闲状态时间\",\"dataType\":\"5\",\"monitorId\":\"298b999ce381\",\"hostId\":\"c9ceb05da32e57c7\",\"monitorItemId\":\"7\",\"dataCategories\":\"CPU\",\"intervalTime\":\"11\",\"source\":\"1\",\"expression\":\"systems.cpu(idle,time)\",\"reportData\":\"21059227.440\",\"dataSubclass\":\"CPU处于空闲状态时间\",\"reportType\":\"5\",\"gatherTime\":\"2024-04-25 15:27:21\"},{\"expression\":\"systems.cpu(hard,c)\",\"dataCategories\":\"CPU\",\"dataType\":\"6\",\"intervalTime\":\"11\",\"monitorId\":\"47acb201db9c\",\"monitorName\":\"用户空间进程的cpu的调度优先级\",\"monitorItemId\":\"8\",\"source\":\"1\",\"hostId\":\"c9ceb05da32e57c7\",\"reportData\":\"825.900\",\"dataSubclass\":\"用户空间进程的CPU的调度优先级\",\"reportType\":\"4\",\"gatherTime\":\"2024-04-25 15:27:21\"},{\"hostId\":\"c9ceb05da32e57c7\",\"source\":\"1\",\"dataCategories\":\"CPU\",\"monitorItemId\":\"9\",\"monitorName\":\"IO读写操作导致CPU处于等待状态的时间\",\"expression\":\"systems.cpu(read,write)\",\"dataType\":\"7\",\"monitorId\":\"b4db00e2c074\",\"intervalTime\":\"11\",\"reportData\":\"13099.940\",\"dataSubclass\":\"IO读写操作导致CPU处于等待状态的时间\",\"reportType\":\"4\",\"gatherTime\":\"2024-04-25 15:27:21\"},{\"dataType\":\"2\",\"expression\":\"systems.cpu(CPU,userTime)\",\"monitorId\":\"400b30a4e014\",\"monitorItemId\":\"2\",\"source\":\"1\",\"hostId\":\"c9ceb05da32e57c7\",\"monitorName\":\"用户态进程占用CPU时间\",\"intervalTime\":\"11\",\"dataCategories\":\"CPU\",\"reportData\":\"11785.430\",\"dataSubclass\":\"用户态进程占用CPU时间\",\"reportType\":\"4\",\"gatherTime\":\"2024-04-25 15:27:21\"},{\"dataCategories\":\"CPU\",\"monitorId\":\"035195a627f7\",\"monitorItemId\":\"3\",\"expression\":\"systems.cpu(system,time)\",\"monitorName\":\"CPU系统占用时间\",\"dataType\":\"3\",\"intervalTime\":\"11\",\"hostId\":\"c9ceb05da32e57c7\",\"source\":\"1\",\"reportData\":\"9784.450\",\"dataSubclass\":\"CPU系统占用时间\",\"reportType\":\"4\",\"gatherTime\":\"2024-04-25 15:27:21\"},{\"monitorId\":\"a60e13866248\",\"hostId\":\"c9ceb05da32e57c7\",\"source\":\"1\",\"monitorName\":\"CPU基本信息\",\"intervalTime\":\"11\",\"expression\":\"systems.cpu(CPU,info)\",\"dataCategories\":\"CPU\",\"dataType\":\"1\",\"monitorItemId\":\"1\",\"reportData\":\"{cpu:0,vendorId:GenuineIntel,family:6,model:94,stepping:3,physicalId:0,coreId:0,cores:1,modelName:Intel Core Processor (Skylake, IBRS),mhz:2995.2,cacheSize:16384,flags:[fpu,vme,de,pse,tsc,msr,pae,mce,cx8,apic,sep,mtrr,pge,mca,cmov,pat,pse36,clflush,mmx,fxsr,sse,sse2,ss,syscall,nx,pdpe1gb,rdtscp,lm,constant_tsc,rep_good,nopl,xtopology,cpuid,tsc_known_freq,pni,pclmulqdq,vmx,ssse3,fma,cx16,pcid,sse4_1,sse4_2,x2apic,movbe,popcnt,tsc_deadline_timer,aes,xsave,avx,f16c,rdrand,hypervisor,lahf_lm,abm,3dnowprefetch,cpuid_fault,invpcid_single,ssbd,ibrs,ibpb,stibp,ibrs_enhanced,tpr_shadow,vnmi,flexpriority,ept,vpid,ept_ad,fsgsbase,tsc_adjust,bmi1,avx2,smep,bmi2,erms,invpcid,rdseed,adx,smap,clflushopt,clwb,sha_ni,xsaveopt,xsavec,xgetbv1,xsaves,avx_vnni,arat,umip,pku,ospke,waitpkg,gfni,vaes,vpclmulqdq,rdpid,movdiri,movdir64b,fsrm,md_clear,arch_capabilities],microcode:0x1},{cpu:1,vendorId:GenuineIntel,family:6,model:94,stepping:3,physicalId:1,coreId:0,cores:1,modelName:Intel Core Processor (Skylake, IBRS),mhz:2995.2,cacheSize:16384,flags:[fpu,vme,de,pse,tsc,msr,pae,mce,cx8,apic,sep,mtrr,pge,mca,cmov,pat,pse36,clflush,mmx,fxsr,sse,sse2,ss,syscall,nx,pdpe1gb,rdtscp,lm,constant_tsc,rep_good,nopl,xtopology,cpuid,tsc_known_freq,pni,pclmulqdq,vmx,ssse3,fma,cx16,pcid,sse4_1,sse4_2,x2apic,movbe,popcnt,tsc_deadline_timer,aes,xsave,avx,f16c,rdrand,hypervisor,lahf_lm,abm,3dnowprefetch,cpuid_fault,invpcid_single,ssbd,ibrs,ibpb,stibp,ibrs_enhanced,tpr_shadow,vnmi,flexpriority,ept,vpid,ept_ad,fsgsbase,tsc_adjust,bmi1,avx2,smep,bmi2,erms,invpcid,rdseed,adx,smap,clflushopt,clwb,sha_ni,xsaveopt,xsavec,xgetbv1,xsaves,avx_vnni,arat,umip,pku,ospke,waitpkg,gfni,vaes,vpclmulqdq,rdpid,movdiri,movdir64b,fsrm,md_clear,arch_capabilities],microcode:0x1}\",\"dataSubclass\":\"CPU基本信息\",\"reportType\":\"2\",\"gatherTime\":\"2024-04-25 15:27:21\"},{\"source\":\"1\",\"monitorId\":\"95e7c4ef20ca\",\"monitorName\":\"所有磁盘的IO信息\",\"intervalTime\":\"11\",\"dataType\":\"2\",\"monitorItemId\":\"4\",\"dataCategories\":\"IO\",\"hostId\":\"c9ceb05da32e57c7\",\"expression\":\"systems.io(host,tps)\",\"reportData\":\"{dm-0:{readCount:59520,mergedReadCount:0,writeCount:5735859,mergedWriteCount:0,readBytes:1405072384,writeBytes:35260452864,readTime:143374,writeTime:13189026,iopsInProgress:1,ioTime:13781098,weightedIO:13332400,name:dm-0,serialNumber:,label:c1-root},dm-1:{readCount:6076,mergedReadCount:0,writeCount:27457,mergedWriteCount:0,readBytes:26759168,writeBytes:112463872,readTime:837,writeTime:17119,iopsInProgress:0,ioTime:1326,weightedIO:17956,name:dm-1,serialNumber:,label:c1-swap},sr0:{readCount:9,mergedReadCount:0,writeCount:0,mergedWriteCount:0,readBytes:1536,writeBytes:0,readTime:0,writeTime:0,iopsInProgress:0,ioTime:4,weightedIO:0,name:sr0,serialNumber:,label:},vda:{readCount:64353,mergedReadCount:3722,writeCount:3983623,mergedWriteCount:1782898,readBytes:1457904128,writeBytes:35402612224,readTime:147573,writeTime:13632933,iopsInProgress:0,ioTime:13781859,weightedIO:13780507,name:vda,serialNumber:,label:},vda1:{readCount:2052,mergedReadCount:1,writeCount:44,mergedWriteCount:5,readBytes:18118144,writeBytes:29695488,readTime:271,writeTime:530,iopsInProgress:0,ioTime:647,weightedIO:802,name:vda1,serialNumber:,label:},vda2:{readCount:62198,mergedReadCount:3721,writeCount:3983579,mergedWriteCount:1782893,readBytes:1437008896,writeBytes:35372916736,readTime:147299,writeTime:13632403,iopsInProgress:0,ioTime:13781542,weightedIO:13779702,name:vda2,serialNumber:,label:}}\",\"dataSubclass\":\"所有硬盘的io信息\",\"reportType\":\"2\",\"gatherTime\":\"2024-04-25 15:27:21\"},{\"dataCategories\":\"IO\",\"dataType\":\"1\",\"intervalTime\":\"11\",\"monitorName\":\"磁盘使用率\",\"monitorId\":\"4f4b1a21fba0\",\"source\":\"1\",\"expression\":\"systems.io(host,rtps)\",\"hostId\":\"c9ceb05da32e57c7\",\"monitorItemId\":\"5\",\"reportData\":\"0.000\",\"dataSubclass\":\"磁盘使用率\",\"reportType\":\"1\",\"gatherTime\":\"2024-04-25 15:27:21\"},{\"intervalTime\":\"11\",\"source\":\"1\",\"monitorName\":\"内存使用率\",\"monitorItemId\":\"10\",\"expression\":\"systems.memory(used,m)\",\"dataCategories\":\"memory\",\"dataType\":\"1\",\"monitorId\":\"bab9c35f30b4\",\"hostId\":\"c9ceb05da32e57c7\",\"reportData\":\"19.852\",\"dataSubclass\":\"内存使用率\",\"reportType\":\"1\",\"gatherTime\":\"2024-04-25 15:27:21\"},{\"monitorId\":\"03b6faee8210\",\"monitorName\":\"cpu利用率\",\"monitorItemId\":\"6\",\"source\":\"1\",\"dataCategories\":\"CPU\",\"hostId\":\"c9ceb05da32e57c7\",\"intervalTime\":\"11\",\"dataType\":\"4\",\"expression\":\"systems.cpu(system,utilizationRate)\",\"reportData\":\"0.056\",\"dataSubclass\":\"CPU利用率\",\"reportType\":\"1\",\"gatherTime\":\"2024-04-25 15:27:21\"}]";

        List<HistoryEntity> entityList = JSONObject.parseArray(hisStr, HistoryEntity.class);

        for (HistoryEntity entity : entityList) {
            Class<?> clazz = entity.getClass();
            Field[] fields = clazz.getDeclaredFields();

            StringJoiner columnsJoiner = new StringJoiner(", ", "(", ")");
            StringJoiner valuesJoiner = new StringJoiner(", ", "VALUES (", ")");

            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                Object value;
                try {
                    value = field.get(entity);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    continue;
                }
                columnsJoiner.add(fieldName);
                valuesJoiner.add(formatValue(value));
            }

            String columns = columnsJoiner.toString();
            String values = valuesJoiner.toString();

            String sql = String.format("INSERT INTO %s %s %s;\n", tableName, columns, values);
            sqlBuilder.append(sql);
        }

        String sql = sqlBuilder.toString();
        System.out.println(sql);
    }
    private static String formatValue(Object value) {
        return value instanceof String ? "'" + value + "'" : String.valueOf(value);
    }
}
