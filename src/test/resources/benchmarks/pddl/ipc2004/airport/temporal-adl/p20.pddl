;;
;; PDDL file for the AIPS2000 Planning Competition
;; based on the data generated by the airport simulator Astras.
;;

;; Author: Sebastian Trueg thisshouldbethecurrentdateandtime :(
;; Created with SegmentSplitterAirportExporter 0.1 by Sebastian Trueg <trueg@informatik.uni-freiburg.de>
;;



(define (problem PROBLEM_X)

(:domain airport_durative)

(:objects

      ;; the airplanes (7)
      airplane_CFBEG
      airplane_DAEWH
      airplane_DFBOY
      airplane_WURST
      airplane_HORST
      airplane_HOLGI
      airplane_4XEKD - airplane

      ;; the airplanetypes (1)
      medium - airplanetype

      ;; the directions (2)
      north
      south - direction

      ;; the segments (44)
      seg_09Thresh_0_100
      seg_09Help_0_100
      seg_09_0_150
      seg_A_09_0_100
      seg_Rwy_0_1300
      seg_27Thresh_0_100
      seg_27Help_0_100
      seg_27_0_150
      seg_B_27_0_100
      seg_N1_N2_0_100
      seg_N1_A_0_100
      seg_N2_N3_1_0_86
      seg_N2_N3_0_85
      seg_N3_N_0_100
      seg_N_N4_0_100
      seg_N_C_0_100
      seg_N4_N5_1_0_85
      seg_N4_N5_0_86
      seg_N5_N6_0_100
      seg_N6_B_0_100
      seg_C_C2_0_100
      seg_C2_C3_0_100
      seg_C3_C4_0_50
      seg_C3_C_A01_0_100
      seg_C3_C_B01_0_100
      seg_C4_C5_0_50
      seg_C5_C6_0_50
      seg_C5_C_A02_0_100
      seg_C5_C_B02_0_100
      seg_C6_C7_0_100
      seg_A01_0_100
      seg_A02_0_100
      seg_B01_0_100
      seg_B02_0_100
      seg_N2_N3_2_0_86
      seg_N2_N3_3_0_86
      seg_N2_N3_4_0_85
      seg_N2_N3_5_0_86
      seg_N2_N3_6_0_86
      seg_N4_N5_2_0_86
      seg_N4_N5_3_0_86
      seg_N4_N5_4_0_85
      seg_N4_N5_5_0_86
      seg_N4_N5_6_0_86 - segment
)

(:init

      (at-segment airplane_CFBEG seg_N4_N5_1_0_85)
      (at-segment airplane_DAEWH seg_A02_0_100)
      (at-segment airplane_DFBOY seg_B02_0_100)
      (at-segment airplane_WURST seg_A01_0_100)
      (at-segment airplane_HORST seg_B01_0_100)
      (at-segment airplane_HOLGI seg_N4_N5_2_0_86)
      (at-segment airplane_4XEKD seg_Rwy_0_1300)

      (blocked seg_N4_N5_1_0_85 airplane_CFBEG)
      (blocked seg_A02_0_100 airplane_DAEWH)
      (blocked seg_B02_0_100 airplane_DFBOY)
      (blocked seg_A01_0_100 airplane_WURST)
      (blocked seg_B01_0_100 airplane_HORST)
      (blocked seg_N4_N5_2_0_86 airplane_HOLGI)
      (blocked seg_Rwy_0_1300 airplane_4XEKD)
      (blocked seg_N4_N5_2_0_86 airplane_CFBEG)
      (blocked seg_N4_N5_3_0_86 airplane_HOLGI)
      (blocked seg_09_0_150 airplane_4XEKD)

      (can-move seg_A01_0_100 seg_C3_C_A01_0_100 north)
      (can-move seg_C3_C_A01_0_100 seg_C3_C4_0_50 north)
      (can-move seg_C3_C4_0_50 seg_C4_C5_0_50 south)
      (can-move seg_C4_C5_0_50 seg_C3_C4_0_50 north)
      (can-move seg_C3_C4_0_50 seg_C2_C3_0_100 north)
      (can-move seg_C2_C3_0_100 seg_C_C2_0_100 north)
      (can-move seg_C_C2_0_100 seg_N_C_0_100 north)
      (can-move seg_N_C_0_100 seg_N3_N_0_100 north)
      (can-move seg_N3_N_0_100 seg_N2_N3_0_85 north)
      (can-move seg_N2_N3_0_85 seg_N2_N3_6_0_86 north)
      (can-move seg_N2_N3_6_0_86 seg_N2_N3_5_0_86 north)
      (can-move seg_N2_N3_5_0_86 seg_N2_N3_4_0_85 north)
      (can-move seg_N2_N3_4_0_85 seg_N2_N3_3_0_86 north)
      (can-move seg_N2_N3_3_0_86 seg_N2_N3_2_0_86 north)
      (can-move seg_N2_N3_2_0_86 seg_N2_N3_1_0_86 north)
      (can-move seg_N2_N3_1_0_86 seg_N1_N2_0_100 north)
      (can-move seg_N1_N2_0_100 seg_N1_A_0_100 north)
      (can-move seg_N1_A_0_100 seg_A_09_0_100 south)
      (can-move seg_A_09_0_100 seg_09_0_150 south)
      (can-move seg_09_0_150 seg_Rwy_0_1300 south)
      (can-move seg_Rwy_0_1300 seg_27_0_150 south)
      (can-move seg_27_0_150 seg_B_27_0_100 south)
      (can-move seg_B_27_0_100 seg_N6_B_0_100 north)
      (can-move seg_N6_B_0_100 seg_N5_N6_0_100 north)
      (can-move seg_N5_N6_0_100 seg_N4_N5_0_86 north)
      (can-move seg_N4_N5_0_86 seg_N4_N5_6_0_86 north)
      (can-move seg_N4_N5_6_0_86 seg_N4_N5_5_0_86 north)
      (can-move seg_N4_N5_5_0_86 seg_N4_N5_4_0_85 north)
      (can-move seg_N4_N5_4_0_85 seg_N4_N5_3_0_86 north)
      (can-move seg_N4_N5_3_0_86 seg_N4_N5_2_0_86 north)
      (can-move seg_N4_N5_2_0_86 seg_N4_N5_1_0_85 north)
      (can-move seg_N4_N5_1_0_85 seg_N_N4_0_100 north)
      (can-move seg_N_N4_0_100 seg_N_C_0_100 north)
      (can-move seg_N_C_0_100 seg_C_C2_0_100 south)
      (can-move seg_C_C2_0_100 seg_C2_C3_0_100 south)
      (can-move seg_C2_C3_0_100 seg_C3_C_A01_0_100 south)
      (can-move seg_C3_C_A01_0_100 seg_A01_0_100 south)
      (can-move seg_A02_0_100 seg_C5_C_A02_0_100 north)
      (can-move seg_C5_C_A02_0_100 seg_C5_C6_0_50 north)
      (can-move seg_C5_C6_0_50 seg_C6_C7_0_100 south)
      (can-move seg_C6_C7_0_100 seg_C5_C6_0_50 north)
      (can-move seg_C5_C6_0_50 seg_C4_C5_0_50 north)
      (can-move seg_C2_C3_0_100 seg_C3_C4_0_50 south)
      (can-move seg_C4_C5_0_50 seg_C5_C_A02_0_100 south)
      (can-move seg_C5_C_A02_0_100 seg_A02_0_100 south)
      (can-move seg_B01_0_100 seg_C3_C_B01_0_100 north)
      (can-move seg_C3_C_B01_0_100 seg_C3_C4_0_50 north)
      (can-move seg_C2_C3_0_100 seg_C3_C_B01_0_100 south)
      (can-move seg_C3_C_B01_0_100 seg_B01_0_100 south)
      (can-move seg_B02_0_100 seg_C5_C_B02_0_100 north)
      (can-move seg_C5_C_B02_0_100 seg_C5_C6_0_50 north)
      (can-move seg_C4_C5_0_50 seg_C5_C_B02_0_100 south)
      (can-move seg_C5_C_B02_0_100 seg_B02_0_100 south)

      (can-pushback seg_A01_0_100 seg_C3_C_A01_0_100 south)
      (can-pushback seg_C3_C_A01_0_100 seg_C3_C4_0_50 south)
      (can-pushback seg_C3_C4_0_50 seg_C4_C5_0_50 north)
      (can-pushback seg_A02_0_100 seg_C5_C_A02_0_100 south)
      (can-pushback seg_C5_C_A02_0_100 seg_C5_C6_0_50 south)
      (can-pushback seg_C5_C6_0_50 seg_C6_C7_0_100 north)
      (can-pushback seg_B01_0_100 seg_C3_C_B01_0_100 south)
      (can-pushback seg_C3_C_B01_0_100 seg_C3_C4_0_50 south)
      
      (can-pushback seg_B02_0_100 seg_C5_C_B02_0_100 south)
      (can-pushback seg_C5_C_B02_0_100 seg_C5_C6_0_50 south)
      

      (facing airplane_CFBEG north)
      (facing airplane_DAEWH south)
      (facing airplane_DFBOY south)
      (facing airplane_WURST south)
      (facing airplane_HORST south)
      (facing airplane_HOLGI north)
      (facing airplane_4XEKD south)

      (has-type airplane_CFBEG medium)
      (has-type airplane_DAEWH medium)
      (has-type airplane_DFBOY medium)
      (has-type airplane_WURST medium)
      (has-type airplane_HORST medium)
      (has-type airplane_HOLGI medium)
      (has-type airplane_4XEKD medium)

      (is-blocked seg_09Help_0_100 medium seg_A_09_0_100 north)
      (is-blocked seg_09_0_150 medium seg_A_09_0_100 north)
      (is-blocked seg_N1_A_0_100 medium seg_A_09_0_100 south)
      (is-blocked seg_27_0_150 medium seg_B_27_0_100 north)
      (is-blocked seg_27Help_0_100 medium seg_B_27_0_100 north)
      (is-blocked seg_N6_B_0_100 medium seg_B_27_0_100 south)
      (is-blocked seg_N2_N3_1_0_86 medium seg_N1_N2_0_100 north)
      (is-blocked seg_N1_A_0_100 medium seg_N1_N2_0_100 south)
      (is-blocked seg_A_09_0_100 medium seg_N1_A_0_100 north)
      (is-blocked seg_N1_N2_0_100 medium seg_N1_A_0_100 south)
      (is-blocked seg_N2_N3_2_0_86 medium seg_N2_N3_1_0_86 north)
      (is-blocked seg_N1_N2_0_100 medium seg_N2_N3_1_0_86 south)
      (is-blocked seg_N3_N_0_100 medium seg_N2_N3_0_85 north)
      (is-blocked seg_N2_N3_6_0_86 medium seg_N2_N3_0_85 south)
      (is-blocked seg_N_N4_0_100 medium seg_N3_N_0_100 north)
      (is-blocked seg_N_C_0_100 medium seg_N3_N_0_100 north)
      (is-blocked seg_N2_N3_0_85 medium seg_N3_N_0_100 south)
      (is-blocked seg_N4_N5_1_0_85 medium seg_N_N4_0_100 north)
      (is-blocked seg_N3_N_0_100 medium seg_N_N4_0_100 south)
      (is-blocked seg_N_C_0_100 medium seg_N_N4_0_100 south)
      (is-blocked seg_C_C2_0_100 medium seg_N_C_0_100 north)
      (is-blocked seg_N3_N_0_100 medium seg_N_C_0_100 south)
      (is-blocked seg_N_N4_0_100 medium seg_N_C_0_100 south)
      (is-blocked seg_N4_N5_2_0_86 medium seg_N4_N5_1_0_85 north)
      (is-blocked seg_N_N4_0_100 medium seg_N4_N5_1_0_85 south)
      (is-blocked seg_N5_N6_0_100 medium seg_N4_N5_0_86 north)
      (is-blocked seg_N4_N5_6_0_86 medium seg_N4_N5_0_86 south)
      (is-blocked seg_N6_B_0_100 medium seg_N5_N6_0_100 north)
      (is-blocked seg_N4_N5_0_86 medium seg_N5_N6_0_100 south)
      (is-blocked seg_B_27_0_100 medium seg_N6_B_0_100 north)
      (is-blocked seg_N5_N6_0_100 medium seg_N6_B_0_100 south)
      (is-blocked seg_C2_C3_0_100 medium seg_C_C2_0_100 north)
      (is-blocked seg_N_C_0_100 medium seg_C_C2_0_100 south)
      (is-blocked seg_C3_C4_0_50 medium seg_C2_C3_0_100 north)
      (is-blocked seg_C3_C_A01_0_100 medium seg_C2_C3_0_100 north)
      (is-blocked seg_C3_C_B01_0_100 medium seg_C2_C3_0_100 north)
      (is-blocked seg_C_C2_0_100 medium seg_C2_C3_0_100 south)
      (is-blocked seg_C4_C5_0_50 medium seg_C3_C4_0_50 north)
      (is-blocked seg_C2_C3_0_100 medium seg_C3_C4_0_50 south)
      (is-blocked seg_C3_C_A01_0_100 medium seg_C3_C4_0_50 south)
      (is-blocked seg_C3_C_B01_0_100 medium seg_C3_C4_0_50 south)
      (is-blocked seg_A01_0_100 medium seg_C3_C_A01_0_100 north)
      (is-blocked seg_C2_C3_0_100 medium seg_C3_C_A01_0_100 south)
      (is-blocked seg_C3_C4_0_50 medium seg_C3_C_A01_0_100 south)
      (is-blocked seg_C3_C_B01_0_100 medium seg_C3_C_A01_0_100 south)
      (is-blocked seg_B01_0_100 medium seg_C3_C_B01_0_100 north)
      (is-blocked seg_C2_C3_0_100 medium seg_C3_C_B01_0_100 south)
      (is-blocked seg_C3_C4_0_50 medium seg_C3_C_B01_0_100 south)
      (is-blocked seg_C3_C_A01_0_100 medium seg_C3_C_B01_0_100 south)
      (is-blocked seg_C5_C6_0_50 medium seg_C4_C5_0_50 north)
      (is-blocked seg_C5_C_A02_0_100 medium seg_C4_C5_0_50 north)
      (is-blocked seg_C5_C_B02_0_100 medium seg_C4_C5_0_50 north)
      (is-blocked seg_C3_C4_0_50 medium seg_C4_C5_0_50 south)
      (is-blocked seg_C6_C7_0_100 medium seg_C5_C6_0_50 north)
      (is-blocked seg_C4_C5_0_50 medium seg_C5_C6_0_50 south)
      (is-blocked seg_C5_C_A02_0_100 medium seg_C5_C6_0_50 south)
      (is-blocked seg_C5_C_B02_0_100 medium seg_C5_C6_0_50 south)
      (is-blocked seg_A02_0_100 medium seg_C5_C_A02_0_100 north)
      (is-blocked seg_C4_C5_0_50 medium seg_C5_C_A02_0_100 south)
      (is-blocked seg_C5_C6_0_50 medium seg_C5_C_A02_0_100 south)
      (is-blocked seg_C5_C_B02_0_100 medium seg_C5_C_A02_0_100 south)
      (is-blocked seg_B02_0_100 medium seg_C5_C_B02_0_100 north)
      (is-blocked seg_C4_C5_0_50 medium seg_C5_C_B02_0_100 south)
      (is-blocked seg_C5_C6_0_50 medium seg_C5_C_B02_0_100 south)
      (is-blocked seg_C5_C_A02_0_100 medium seg_C5_C_B02_0_100 south)
      (is-blocked seg_C5_C6_0_50 medium seg_C6_C7_0_100 south)
      (is-blocked seg_C3_C_A01_0_100 medium seg_A01_0_100 south)
      (is-blocked seg_C5_C_A02_0_100 medium seg_A02_0_100 south)
      (is-blocked seg_C3_C_B01_0_100 medium seg_B01_0_100 south)
      (is-blocked seg_C5_C_B02_0_100 medium seg_B02_0_100 south)
      (is-blocked seg_N2_N3_3_0_86 medium seg_N2_N3_2_0_86 north)
      (is-blocked seg_N2_N3_1_0_86 medium seg_N2_N3_2_0_86 south)
      (is-blocked seg_N2_N3_4_0_85 medium seg_N2_N3_3_0_86 north)
      (is-blocked seg_N2_N3_2_0_86 medium seg_N2_N3_3_0_86 south)
      (is-blocked seg_N2_N3_5_0_86 medium seg_N2_N3_4_0_85 north)
      (is-blocked seg_N2_N3_3_0_86 medium seg_N2_N3_4_0_85 south)
      (is-blocked seg_N2_N3_6_0_86 medium seg_N2_N3_5_0_86 north)
      (is-blocked seg_N2_N3_4_0_85 medium seg_N2_N3_5_0_86 south)
      (is-blocked seg_N2_N3_0_85 medium seg_N2_N3_6_0_86 north)
      (is-blocked seg_N2_N3_5_0_86 medium seg_N2_N3_6_0_86 south)
      (is-blocked seg_N4_N5_3_0_86 medium seg_N4_N5_2_0_86 north)
      (is-blocked seg_N4_N5_1_0_85 medium seg_N4_N5_2_0_86 south)
      (is-blocked seg_N4_N5_4_0_85 medium seg_N4_N5_3_0_86 north)
      (is-blocked seg_N4_N5_2_0_86 medium seg_N4_N5_3_0_86 south)
      (is-blocked seg_N4_N5_5_0_86 medium seg_N4_N5_4_0_85 north)
      (is-blocked seg_N4_N5_3_0_86 medium seg_N4_N5_4_0_85 south)
      (is-blocked seg_N4_N5_6_0_86 medium seg_N4_N5_5_0_86 north)
      (is-blocked seg_N4_N5_4_0_85 medium seg_N4_N5_5_0_86 south)
      (is-blocked seg_N4_N5_0_86 medium seg_N4_N5_6_0_86 north)
      (is-blocked seg_N4_N5_5_0_86 medium seg_N4_N5_6_0_86 south)
      (is-blocked seg_B_27_0_100 medium seg_27_0_150 north)
      (is-blocked seg_27_0_150 medium seg_Rwy_0_1300 north)
      (is-blocked seg_27_0_150 medium seg_09_0_150 north)
      (is-blocked seg_Rwy_0_1300 medium seg_09_0_150 north)
      (is-blocked seg_A_09_0_100 medium seg_09_0_150 south)
      (is-blocked seg_09_0_150 medium seg_Rwy_0_1300 south)
      (is-blocked seg_09_0_150 medium seg_27_0_150 south)
      (is-blocked seg_Rwy_0_1300 medium seg_27_0_150 south)

      (is-moving airplane_CFBEG)
      (is-moving airplane_HOLGI)
      (is-moving airplane_4XEKD)

      (is-pushing airplane_DAEWH)
      (is-pushing airplane_DFBOY)
      (is-pushing airplane_WURST)
      (is-pushing airplane_HORST)

      (is-start-runway seg_27_0_150 north)
      (is-start-runway seg_09_0_150 south)


      (move-back-dir seg_A01_0_100 seg_C3_C_A01_0_100 south)
      (move-back-dir seg_C3_C_A01_0_100 seg_C3_C4_0_50 north)
      (move-back-dir seg_C3_C4_0_50 seg_C4_C5_0_50 north)
      (move-back-dir seg_A02_0_100 seg_C5_C_A02_0_100 south)
      (move-back-dir seg_C5_C_A02_0_100 seg_C5_C6_0_50 north)
      (move-back-dir seg_C5_C6_0_50 seg_C6_C7_0_100 north)
      (move-back-dir seg_B01_0_100 seg_C3_C_B01_0_100 south)
      (move-back-dir seg_C3_C_B01_0_100 seg_C3_C4_0_50 north)
      (move-back-dir seg_C3_C4_0_50 seg_C4_C5_0_50 north)
      (move-back-dir seg_B02_0_100 seg_C5_C_B02_0_100 south)
      (move-back-dir seg_C5_C_B02_0_100 seg_C5_C6_0_50 north)
      (move-back-dir seg_C5_C6_0_50 seg_C6_C7_0_100 north)

      (move-dir seg_A01_0_100 seg_C3_C_A01_0_100 north)
      (move-dir seg_C3_C_A01_0_100 seg_C3_C4_0_50 south)
      (move-dir seg_C3_C4_0_50 seg_C4_C5_0_50 south)
      (move-dir seg_C4_C5_0_50 seg_C3_C4_0_50 north)
      (move-dir seg_C3_C4_0_50 seg_C2_C3_0_100 north)
      (move-dir seg_C2_C3_0_100 seg_C_C2_0_100 north)
      (move-dir seg_C_C2_0_100 seg_N_C_0_100 north)
      (move-dir seg_N_C_0_100 seg_N3_N_0_100 north)
      (move-dir seg_N3_N_0_100 seg_N2_N3_0_85 north)
      (move-dir seg_N2_N3_0_85 seg_N2_N3_6_0_86 north)
      (move-dir seg_N2_N3_6_0_86 seg_N2_N3_5_0_86 north)
      (move-dir seg_N2_N3_5_0_86 seg_N2_N3_4_0_85 north)
      (move-dir seg_N2_N3_4_0_85 seg_N2_N3_3_0_86 north)
      (move-dir seg_N2_N3_3_0_86 seg_N2_N3_2_0_86 north)
      (move-dir seg_N2_N3_2_0_86 seg_N2_N3_1_0_86 north)
      (move-dir seg_N2_N3_1_0_86 seg_N1_N2_0_100 north)
      (move-dir seg_N1_N2_0_100 seg_N1_A_0_100 south)
      (move-dir seg_N1_A_0_100 seg_A_09_0_100 south)
      (move-dir seg_A_09_0_100 seg_09_0_150 south)
      (move-dir seg_09_0_150 seg_Rwy_0_1300 south)
      (move-dir seg_Rwy_0_1300 seg_27_0_150 south)
      (move-dir seg_27_0_150 seg_B_27_0_100 north)
      (move-dir seg_B_27_0_100 seg_N6_B_0_100 north)
      (move-dir seg_N6_B_0_100 seg_N5_N6_0_100 north)
      (move-dir seg_N5_N6_0_100 seg_N4_N5_0_86 north)
      (move-dir seg_N4_N5_0_86 seg_N4_N5_6_0_86 north)
      (move-dir seg_N4_N5_6_0_86 seg_N4_N5_5_0_86 north)
      (move-dir seg_N4_N5_5_0_86 seg_N4_N5_4_0_85 north)
      (move-dir seg_N4_N5_4_0_85 seg_N4_N5_3_0_86 north)
      (move-dir seg_N4_N5_3_0_86 seg_N4_N5_2_0_86 north)
      (move-dir seg_N4_N5_2_0_86 seg_N4_N5_1_0_85 north)
      (move-dir seg_N4_N5_1_0_85 seg_N_N4_0_100 north)
      (move-dir seg_N_N4_0_100 seg_N_C_0_100 south)
      (move-dir seg_N_C_0_100 seg_C_C2_0_100 south)
      (move-dir seg_C_C2_0_100 seg_C2_C3_0_100 south)
      (move-dir seg_C2_C3_0_100 seg_C3_C_A01_0_100 south)
      (move-dir seg_C3_C_A01_0_100 seg_A01_0_100 south)
      (move-dir seg_A02_0_100 seg_C5_C_A02_0_100 north)
      (move-dir seg_C5_C_A02_0_100 seg_C5_C6_0_50 south)
      (move-dir seg_C5_C6_0_50 seg_C6_C7_0_100 south)
      (move-dir seg_C6_C7_0_100 seg_C5_C6_0_50 north)
      (move-dir seg_C5_C6_0_50 seg_C4_C5_0_50 north)
      (move-dir seg_C2_C3_0_100 seg_C3_C4_0_50 south)
      (move-dir seg_C4_C5_0_50 seg_C5_C_A02_0_100 south)
      (move-dir seg_C5_C_A02_0_100 seg_A02_0_100 south)
      (move-dir seg_B01_0_100 seg_C3_C_B01_0_100 north)
      (move-dir seg_C3_C_B01_0_100 seg_C3_C4_0_50 south)
      (move-dir seg_C2_C3_0_100 seg_C3_C_B01_0_100 south)
      (move-dir seg_C3_C_B01_0_100 seg_B01_0_100 south)
      (move-dir seg_B02_0_100 seg_C5_C_B02_0_100 north)
      (move-dir seg_C5_C_B02_0_100 seg_C5_C6_0_50 south)
      (move-dir seg_C4_C5_0_50 seg_C5_C_B02_0_100 south)
      (move-dir seg_C5_C_B02_0_100 seg_B02_0_100 south)

      (occupied seg_N4_N5_1_0_85)
      (occupied seg_A02_0_100)
      (occupied seg_B02_0_100)
      (occupied seg_A01_0_100)
      (occupied seg_B01_0_100)
      (occupied seg_N4_N5_2_0_86)
      (occupied seg_Rwy_0_1300)
      (= (length seg_09Thresh_0_100) 100)
      (= (length seg_09Help_0_100) 100)
      (= (length seg_09_0_150) 150)
      (= (length seg_A_09_0_100) 100)
      (= (length seg_Rwy_0_1300) 1300)
      (= (length seg_27Thresh_0_100) 100)
      (= (length seg_27Help_0_100) 100)
      (= (length seg_27_0_150) 150)
      (= (length seg_B_27_0_100) 100)
      (= (length seg_N1_N2_0_100) 100)
      (= (length seg_N1_A_0_100) 100)
      (= (length seg_N2_N3_1_0_86) 86)
      (= (length seg_N2_N3_0_85) 85)
      (= (length seg_N3_N_0_100) 100)
      (= (length seg_N_N4_0_100) 100)
      (= (length seg_N_C_0_100) 100)
      (= (length seg_N4_N5_1_0_85) 85)
      (= (length seg_N4_N5_0_86) 86)
      (= (length seg_N5_N6_0_100) 100)
      (= (length seg_N6_B_0_100) 100)
      (= (length seg_C_C2_0_100) 100)
      (= (length seg_C2_C3_0_100) 100)
      (= (length seg_C3_C4_0_50) 50)
      (= (length seg_C3_C_A01_0_100) 100)
      (= (length seg_C3_C_B01_0_100) 100)
      (= (length seg_C4_C5_0_50) 50)
      (= (length seg_C5_C6_0_50) 50)
      (= (length seg_C5_C_A02_0_100) 100)
      (= (length seg_C5_C_B02_0_100) 100)
      (= (length seg_C6_C7_0_100) 100)
      (= (length seg_A01_0_100) 100)
      (= (length seg_A02_0_100) 100)
      (= (length seg_B01_0_100) 100)
      (= (length seg_B02_0_100) 100)
      (= (length seg_N2_N3_2_0_86) 86)
      (= (length seg_N2_N3_3_0_86) 86)
      (= (length seg_N2_N3_4_0_85) 85)
      (= (length seg_N2_N3_5_0_86) 86)
      (= (length seg_N2_N3_6_0_86) 86)
      (= (length seg_N4_N5_2_0_86) 86)
      (= (length seg_N4_N5_3_0_86) 86)
      (= (length seg_N4_N5_4_0_85) 85)
      (= (length seg_N4_N5_5_0_86) 86)
      (= (length seg_N4_N5_6_0_86) 86)
      (= (engines airplane_CFBEG) 2)
      (= (engines airplane_DAEWH) 2)
      (= (engines airplane_DFBOY) 2)
      (= (engines airplane_WURST) 2)
      (= (engines airplane_HORST) 2)
      (= (engines airplane_HOLGI) 2)
      (= (engines airplane_4XEKD) 2)
)

(:goal
      (and



            (is-parked airplane_CFBEG seg_A01_0_100)
            (airborne airplane_DAEWH seg_09_0_150)
            (airborne airplane_DFBOY seg_09_0_150)
            (airborne airplane_WURST seg_09_0_150)
            (airborne airplane_HORST seg_09_0_150)
            (is-parked airplane_HOLGI seg_B02_0_100)
            (is-parked airplane_4XEKD seg_B01_0_100)
      )
)

(:metric minimize (total-time))

)

