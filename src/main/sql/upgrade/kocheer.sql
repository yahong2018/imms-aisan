insert into imms.zhxh_kocheer_conter_info(station_id,gid,conter_name,start_did,end_did,ip,port,position,is_use,wiress_power,remark)
select 1 as station_id,
       GID as gid,
			 ConterName as conter_name,
			 StartDID as start_did,
			 EndDID as end_did,
			 IP as ip,
			 Port as port,
			 Position as position,
			 IsUse as is_use,
			 WiressPower as wiress_power,
			 Remark as remark
from data_gather.conterinfo
