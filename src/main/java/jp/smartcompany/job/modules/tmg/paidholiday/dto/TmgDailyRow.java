package jp.smartcompany.job.modules.tmg.paidholiday.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * TMG_DAILY_ROW
 */
@Getter
@Setter
@ToString
public class TmgDailyRow {

    private	String	cCustomerId	;
    private	String	cCompanyId	;
    private	String	cEmployeeId	;
    private	Date	dStartDate	;
    private	Date	dDndDate	;
    private	String	cModifierUserId	;
    private	Date	dModifiedDate	;
    private	String	cModifierProgramId	;
    private	double	nYyyy	;
    private	Date	dYyyyMm	;
    private	Date	dYyyyMmDd	;
    private	String	cStatusFlg	;
    private	String	cNtfStatusFlg	;
    private	String	cErrCode	;
    private	String	cErrMsg	;
    private	double	nOpenTp	;
    private	double	nCloseTp	;
    private	String	cHolFlg	;
    private	String	cWorkingIdP	;
    private	String	cWorkingNameP	;
    private	List<TmgTimeRange>	timeRangeP	;
    private	String	cModifierUserIdP	;
    private	Date	dModifiedDateP	;
    private	double	nLockP	;
    private	double	nRest45P	;
    private	List<TmgTimeRange>	timeRangeN	;
    private	String	cModifierUserIdN	;
    private	Date	dModifiedDateN	;
    private	double	nOpenO	;
    private	double	nCloseO	;
    private	String	cCommentO	;
    private	String	cModifierUserIdO	;
    private	Date	dModifiedDateO	;
    private	String	cWorkingIdR	;
    private	String	cWorkingNameR	;
    private	List<TmgTimeRange>	timeRangeR	;
    private	String	cOwnCommentR	;
    private	String	cBossCommentR	;
    private	String	cModifierUserIdR	;
    private	Date	dModifiedDateR	;
    private	String	cMessage	;
    private	double	nCalc01	;
    private	double	nCalc02	;
    private	double	nCalc03	;
    private	double	nCalc04	;
    private	double	nCalc05	;
    private	double	nCalc06	;
    private	double	nCalc07	;
    private	double	nCalc08	;
    private	double	nCalc09	;
    private	double	nCalc10	;
    private	double	nCalc11	;
    private	double	nCalc12	;
    private	double	nCalc13	;
    private	double	nCalc14	;
    private	double	nCalc15	;
    private	double	nCalc16	;
    private	double	nCalc17	;
    private	double	nCalc18	;
    private	double	nCalc19	;
    private	double	nCalc20	;
    private	double	nCalc21	;
    private	double	nCalc22	;
    private	double	nCalc23	;
    private	double	nCalc24	;
    private	double	nCalc25	;
    private	double	nCalc26	;
    private	double	nCalc27	;
    private	double	nCalc28	;
    private	double	nCalc29	;
    private	double	nCalc30	;
    private	String	cBusinessTripIdP	;
    private	String	cBusinessTripIdR	;
    private	String	cCommentP	;
    private	String	cPatternId	;
    private	String	cRefNtfNo	;
    private	String	cWorkingIdPPre	;
    private	Date	dSubstituted	;
}
