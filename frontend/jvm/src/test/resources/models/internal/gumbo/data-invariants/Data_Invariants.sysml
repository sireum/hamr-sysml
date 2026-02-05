package Data_Invariants {
    private import AADL::*;

    part def Temperature :> AADL::Data;

    part def Temperature_i :> Temperature {
        part degrees : Base_Types::Float_64;

        language "GUMBO" /*{
              // data invariant
              //  data invariants are assumed whenever value of the datatype is consumed
              //                  and must be proved whenever a value of the data type is constructed
              invariants
                inv i1 "Temperature Data Invariant" :
                  degrees >= -459.67; 
                  
                inv i2 "V: real vs neg real":
                  1.0 >= -459.67;
                inv i3 "V: comparing ints":
                  1 + 1  <=  3 - 1 ;
                inv i4 "V: T":
                  true;
                inv i5 "V: !F":
                  not false;
                inv i6 "V: mixed":
                  false != ( 1 + 1  <=  3 - 1 );
                inv i7 "V: logical ops":
                  true | true  or   true & ( not true ) and true  ;
                inv i8 "V: arith ops":
                  2 - 1 -  5 / 5  +  1 * 5  == 5;
                  
                inv i9 "litInters":
                  1[z] > 0[z] and 8[z8] > 7[z8] and 16[z16] > 15[z16] and 32[z32] > 31[z32] and 64[z64] > 63[z64] and
                  
                  1[n] > 0[n] and 8[n8] > 7[n8] and 16[n16] > 15[n16] and 32[n32] > 31[n32] and 64[n64] > 63[n64] and
                
                  9[s8] > 8[s8]  and  16[s16] > 15[s16]  and  32[s32] > 31[s32]  and  64[s64] > 63[s64]  and 
                  
                  1[u1] > 0[u1] and 1[u2] > 0[u2] and 1[u3] > 0[u3] and 1[u4] > 0[u4] and 1[u5] > 0[u5] and 1[u6] > 0[u6] and 1[u7] > 0[u7] and 1[u8] > 0[u8] and 1[u9] > 0[u9] and 
                  1[u10] > 0[u10] and 1[u11] > 0[u11] and 1[u12] > 0[u12] and 1[u13] > 0[u13] and 1[u14] > 0[u14] and 1[u15] > 0[u15] and 1[u16] > 0[u16] and 1[u17] > 0[u17] and 1[u18] > 0[u18] and 1[u19] > 0[u19] and 
                  1[u20] > 0[u20] and 1[u21] > 0[u21] and 1[u22] > 0[u22] and 1[u23] > 0[u23] and 1[u24] > 0[u24] and 1[u25] > 0[u25] and 1[u26] > 0[u26] and 1[u27] > 0[u27] and 1[u28] > 0[u28] and 1[u29] > 0[u29] and 
                  1[u30] > 0[u30] and 1[u31] > 0[u31] and 1[u32] > 0[u32] and 1[u33] > 0[u33] and 1[u34] > 0[u34] and 1[u35] > 0[u35] and 1[u36] > 0[u36] and 1[u37] > 0[u37] and 1[u38] > 0[u38] and 1[u39] > 0[u39] and
                  1[u40] > 0[u40] and 1[u41] > 0[u41] and 1[u42] > 0[u42] and 1[u43] > 0[u43] and 1[u44] > 0[u44] and 1[u45] > 0[u45] and 1[u46] > 0[u46] and 1[u47] > 0[u47] and 1[u48] > 0[u48] and 1[u49] > 0[u49] and
                  1[u50] > 0[u50] and 1[u51] > 0[u51] and 1[u52] > 0[u52] and 1[u53] > 0[u53] and 1[u54] > 0[u54] and 1[u55] > 0[u55] and 1[u56] > 0[u56] and 1[u57] > 0[u57] and 1[u58] > 0[u58] and 1[u59] > 0[u59] and
                  1[u60] > 0[u60] and 1[u61] > 0[u61] and 1[u62] > 0[u62] and 1[u63] > 0[u63] and 1[u64] > 0[u64] and
                    
                  1.0[f32] > 0.0[f32]  and  1.0[f64] > 0.0[f64]  and
                  
                  1.0[r] > 0.0[r]  and

                  -1.0[f32] == -1.0[f32] ;

                inv i10 "string literal and interpolates":
                  "bbbbb" != "aaaaa"  and

                  "astring"[string] == "astring"[string];
                  
                inv i11 "char literal and interpolates":
                  //'c' == 'c' and

                  //"\n"[c] == "\n"[c] and  TODO: Slang c"..." only allows a single character?
                  
                  "c"[c] == "c"[c];

                //inv i10 "I: Real vs Int":
                //  1.0 >= 0;
                
                //inv i11 "I: int expression":
                //  ((1) + 2);
                //inv i12 "I: logical op on ints":
                //  1 and 2;
                //inv i13 "I: arith op on bools":
                //  (1 = 1) + true;
            }*/
    }

    part def SetPoint :> AADL::Data;

    part def SetPoint_i :> SetPoint {
        part low : Temperature_i;
        part high : Temperature_i;

        language "GUMBO" /*{       
          invariants
            inv i1 "SetPoint Data Invariant":
              low.degrees >= 70.0  and
              high.degrees <= 100.0  and
              low.degrees <= high.degrees ;
        }*/
    }

    part def s :> AADL::System;

    part def s_impl :> s {
        part p : p_impl;
    }

    part def p :> AADL::Process;

    part def p_impl :> p {
        part t : Data_Invariants::t;
    }

    part def t :> AADL::Thread {
        out port setPoint : AADL::DataPort {
            out :>> type : SetPoint_i;
        }

        out port temp : AADL::DataPort {
            out :>> type : Temperature_i;
        }
    }
}