import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'jhi-rehabilitation-group-detail',
    templateUrl: './rehabilitation-group-evaluation-detail.component.html'
})
export class RehabilitationGroupDetailEvaluationComponent implements OnInit {
    rehabilitationGroup: any;
    barChartOptionsStadistics;
    barChartLabelsStadistics;
    barChartTypeStadistics;
    barChartLegendStadistics = true;
    barChartDataStadistics;
    lineChartColorsStadistics;
    barChartOptionsDistribution;
    barChartLabelsDistribution;
    barChartTypeDistribution;
    barChartLegendDistribution = true;
    barChartDataDistribution;
    lineChartColorsDistribution = [];
    step = 0;
    selectedSesion;

    barChartOptionsSessionMinor;
    barChartLabelsSessionMinor;
    barChartTypeSessionMinor;
    barChartLegendSessionMinor = false;
    barChartDataSessionMinor;
    lineChartColorsSessionMinor = [];

    barChartOptionsSessionMayor;
    barChartLabelsSessionMayor;
    barChartTypeSessionMayor;
    barChartLegendSessionMayor = false;
    barChartDataSessionMayor;
    lineChartColorsSessionMayor = [];
    // barChartOptions: ChartOptions = {
    //   responsive: true,
    //   // We use these empty structures as placeholders for dynamic theming.
    //   scales: {xAxes: [{}], yAxes: [{}]},
    //   plugins: {
    //     datalabels: {
    //       anchor: 'end',
    //       align: 'end',
    //     }
    //   }
    // };
    // barChartLabels: Label[] = ['2006', '2007', '2008', '2009', '2010', '2011', '2012'];
    // barChartType: ChartType = 'bar';
    // barChartLegend = true;
    // barChartData: ChartDataSets[] = [
    //   {data: [65, 59, 80, 81, 56, 55, 40], label: 'Series A'},
    //   {data: [28, 48, 40, 19, 86, 27, 90], label: 'Series B'}
    // ];
    constructor(protected activatedRoute: ActivatedRoute) {}

    renderGraphMinorDistribution(rehabilitationGroup) {
        this.barChartOptionsDistribution = {
            responsive: true,
            // We use these empty structures as placeholders for dynamic theming.
            scales: { xAxes: [{}], yAxes: [{}] },
            plugins: {
                datalabels: {
                    anchor: 'end',
                    align: 'end'
                }
            }
        };
        this.barChartLabelsDistribution = [];
        this.barChartTypeDistribution = 'line';
        this.barChartLegendDistribution = true;
        this.lineChartColorsDistribution = [
            {
                borderColor: '#8c24a8',
                backgroundColor: 'rgba(140, 36, 168, 0.52)'
            },
            {
                borderColor: '#f44336',
                backgroundColor: 'rgba(244, 64, 52, 0.58)'
            }
        ];
        const minorEvents = { data: [], label: 'Eventos menores' };
        const mayorEvents = { data: [], label: 'Eventos mayores' };
        for (let i = 0; i < 60; i++) {
            this.barChartLabelsDistribution.push(i + 1 + '');
            minorEvents.data.push(rehabilitationGroup.panelData.distributionMinorEvents[i].minorEventsPerSesion);
            mayorEvents.data.push(rehabilitationGroup.panelData.distributionMinorEvents[i].mayorEventsPerSesion);
        }
        this.barChartDataDistribution = [minorEvents, mayorEvents];
    }
    selectSesion(e) {
        const session = this.rehabilitationGroup.panelData.distributionMinorEvents[this.selectedSesion];
        this.barChartOptionsSessionMinor = {
            responsive: true,
            legend: {
                position: 'left'
            },
            plugins: {
                datalabels: {}
            }
        };
        this.barChartTypeSessionMinor = 'pie';
        this.barChartLegendSessionMinor = true;

        this.barChartDataSessionMinor = [];
        this.barChartLabelsSessionMinor = [];
        for (let j = 0; j < session.minorEvents.length; j++) {
            const minorEvent = session.minorEvents[j];
            this.barChartLabelsSessionMinor.push(minorEvent.description);
            this.barChartDataSessionMinor.push(minorEvent.id);
        }

        this.barChartOptionsSessionMayor = {
            responsive: true,
            legend: {
                position: 'left'
            },
            plugins: {
                datalabels: {}
            }
        };
        this.barChartLabelsSessionMayor = [];
        this.barChartTypeSessionMayor = 'pie';
        this.barChartLegendSessionMayor = true;

        this.barChartDataSessionMayor = [];
        this.barChartLabelsSessionMayor = [];
        for (let j = 0; j < session.mayorEvents.length; j++) {
            const mayorEvent = session.mayorEvents[j];
            this.barChartLabelsSessionMayor.push(mayorEvent.description);
            this.barChartDataSessionMayor.push(mayorEvent.id);
        }
    }
    renderGraphStadisctics(rehabilitationGroup) {
        this.barChartOptionsStadistics = {
            responsive: true
            // // We use these empty structures as placeholders for dynamic theming.
            // scales: {xAxes: [{}], yAxes: [{}]},
            // plugins: {
            //   datalabels: {
            //     anchor: 'end',
            //     align: 'end',
            //   }
            // }
        };
        this.lineChartColorsStadistics = [
            {
                backgroundColor: 'rgba(0, 138, 124, 0.63)'
            }
        ];
        this.barChartLabelsStadistics = [
            'Abandono por causa no médica',
            'Mejoría en su capacidad funcional',
            'Mejoría en su control perfil lipídico',
            'Mejoría en su control glicémico',
            'Reducción de 5% de peso basal',
            'Suspendieron tabaquismo',
            'Reincorporan a la actividad laboral',
            'Adherencia al ejercicio físico'
        ];

        this.barChartTypeStadistics = 'horizontalBar';
        this.barChartLegendStadistics = true;
        this.barChartDataStadistics = [
            {
                data: [
                    rehabilitationGroup.panelData.abandonmentNonMedicalCausePercentage,
                    rehabilitationGroup.panelData.improvementFunctionalCapacityPercentage,
                    rehabilitationGroup.panelData.improvementLipidicProfilePercentage,
                    rehabilitationGroup.panelData.improvementGlycemicControlPercentage,
                    rehabilitationGroup.panelData.weightReductionPercentage,
                    rehabilitationGroup.panelData.suspendedSmokingPercentage,
                    rehabilitationGroup.panelData.returnWorkActivityPercentage,
                    rehabilitationGroup.panelData.exerciseAdherenceRestPercentage
                ],
                label: 'Porcentaje de pacientes'
            }
        ];
    }

    setStep(index: number) {
        this.step = index;
    }
    ngOnInit() {
        this.activatedRoute.data.subscribe(({ rehabilitationGroup }) => {
            this.rehabilitationGroup = rehabilitationGroup;
            this.renderGraphStadisctics(this.rehabilitationGroup);
            this.renderGraphMinorDistribution(this.rehabilitationGroup);
        });
    }

    previousState() {
        window.history.back();
    }
}
