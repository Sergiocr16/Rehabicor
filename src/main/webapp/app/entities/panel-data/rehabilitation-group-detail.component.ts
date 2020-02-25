import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { IMinorEvent } from 'app/shared/model/minor-event.model';
import { MinorEventService } from 'app/entities/minor-event/minor-event.service';
import { ModalService } from 'app/shared/util/modal.service';
import { GlobalVariablesService } from 'app/shared/util/global-variables.service';
import { MayorEventService } from 'app/entities/mayor-event/mayor-event.service';
import { IMayorEvent } from 'app/shared/model/mayor-event.model';

@Component({
    selector: 'jhi-rehabilitation-group-detail',
    templateUrl: './rehabilitation-group-detail.component.html'
})
export class RehabilitationGroupDetailPanelDataComponent implements OnInit {
    minorEvents: IMinorEvent[];
    mayorEvents: IMayorEvent[];
    eventsGraph: IMinorEvent[];
    displayEventsSessionsGraph = false;
    barChartEventsPerSessionDistribution;

    barChartEventsPerSessionDistributionLabels;
    barChartEventsPerSessionDistributionType;
    barChartEventsPerSessionDistributionLegend;
    barChartEventsPerSessionDistributionColors;
    barChartOptionsEventsPerSessionDistribution;
    barChartDataEventsPerSessionDistribution;

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
    eventType = '1';
    labelEventType = 'Seleccione el evento mayor';
    eventPerSessionId;
    graphicEventsPerSessions = [];

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
    constructor(
        protected activatedRoute: ActivatedRoute,
        protected minorEventService: MinorEventService,
        protected mayorEventService: MayorEventService,
        protected modal: ModalService,
        private global: GlobalVariablesService
    ) {}

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

    selectEventType(e) {
        this.labelEventType = this.eventType === '1' ? 'Seleccione el evento mayor' : 'Seleccione el evento menor';
        this.graphicEventsPerSessions = this.eventType === '1' ? this.mayorEvents : this.minorEvents;
        this.displayEventsSessionsGraph = false;
        this.eventPerSessionId = undefined;
    }

    showGraphEventPerSessions(e) {
        if (this.eventType === '2') {
            this.minorEventService
                .graphicMinorEventSession({
                    groupId: this.rehabilitationGroup.id,
                    rehabilitationId: this.global.rehabCenter,
                    minorEventId: this.eventPerSessionId
                })
                .subscribe((res: HttpResponse<[]>) => this.showGraphEventPerSessionsRenderMinor(res.body, res.headers));
        } else {
            this.mayorEventService
                .graphicMayorEventSession({
                    groupId: this.rehabilitationGroup.id,
                    rehabilitationId: this.global.rehabCenter,
                    mayorEventId: this.eventPerSessionId
                })
                .subscribe((res: HttpResponse<[]>) => this.showGraphEventPerSessionsRenderMayor(res.body, res.headers));
        }
    }

    protected findEvent(id: any) {
        if (this.eventType === '1') {
            for (const event of this.mayorEvents) {
                if (event.id === id) {
                    return event.description;
                }
            }
        } else {
            for (const event of this.minorEvents) {
                if (event.id === id) {
                    return event.description;
                }
            }
        }
    }
    protected showGraphEventPerSessionsRenderMinor(data: IMinorEvent[], headers: HttpHeaders) {
        this.eventsGraph = data;
        this.barChartOptionsEventsPerSessionDistribution = {
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
        this.barChartEventsPerSessionDistributionLabels = [];
        this.barChartEventsPerSessionDistributionType = 'line';
        this.barChartEventsPerSessionDistributionLegend = true;
        this.barChartEventsPerSessionDistributionColors = [
            {
                borderColor: '#33a88c',
                backgroundColor: 'rgba(56,168,142,0.52)'
            }
        ];

        const events = { data: [], label: this.findEvent(this.eventPerSessionId) };
        for (let i = 0; i < 60; i++) {
            this.barChartEventsPerSessionDistributionLabels.push(i + 1 + '');
            events.data.push(parseInt(this.eventsGraph[i].description, 10));
        }
        this.barChartDataEventsPerSessionDistribution = [events];
        this.displayEventsSessionsGraph = true;
    }
    protected showGraphEventPerSessionsRenderMayor(data: IMayorEvent[], headers: HttpHeaders) {
        this.eventsGraph = data;
        this.barChartOptionsEventsPerSessionDistribution = {
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
        this.barChartEventsPerSessionDistributionLabels = [];
        this.barChartEventsPerSessionDistributionType = 'line';
        this.barChartEventsPerSessionDistributionLegend = true;
        this.barChartEventsPerSessionDistributionColors = [
            {
                borderColor: '#33a88c',
                backgroundColor: 'rgba(56,168,142,0.52)'
            }
        ];
        const events = { data: [], label: this.findEvent(this.eventPerSessionId) };
        for (let i = 0; i < 60; i++) {
            this.barChartEventsPerSessionDistributionLabels.push(i + 1 + '');
            events.data.push(parseInt(this.eventsGraph[i].description, 10));
        }
        this.barChartDataEventsPerSessionDistribution = [events];
        this.displayEventsSessionsGraph = true;
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
        this.lineChartColorsSessionMinor.push({
            backgroundColor: []
        });
        for (let x = 0; x < session.minorEvents.length; x++) {
            const minorEvent = session.minorEvents[x];
            this.barChartLabelsSessionMinor.push(minorEvent.description);
            this.barChartDataSessionMinor.push(minorEvent.id);
            this.lineChartColorsSessionMinor[0].backgroundColor.push(this.randomColor(x));
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
        this.lineChartColorsSessionMayor.push({
            backgroundColor: []
        });
        this.barChartLabelsSessionMayor = [];
        this.barChartTypeSessionMayor = 'pie';
        this.barChartLegendSessionMayor = false;
        this.barChartDataSessionMayor = [];
        this.barChartLabelsSessionMayor = [];

        for (let j = 0; j < session.mayorEvents.length; j++) {
            const mayorEvent = session.mayorEvents[j];
            this.barChartLabelsSessionMayor.push(mayorEvent.description);
            this.barChartDataSessionMayor.push(mayorEvent.id);
            this.lineChartColorsSessionMayor[0].backgroundColor.push(this.randomColor(j));
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
            this.loadAllMinorEvents();
            this.loadAllMayorEvents();
        });
    }

    loadAllMinorEvents() {
        this.minorEventService
            .query({
                page: 0,
                size: 1000,
                rehabilitationId: this.global.rehabCenter
            })
            .subscribe((res: HttpResponse<[]>) => this.paginateMinorEvents(res.body, res.headers));
    }
    protected paginateMinorEvents(data: [], headers: HttpHeaders) {
        this.minorEvents = [];
        for (let i = 0; i < data.length; i++) {
            this.minorEvents.push(data[i]);
        }
    }

    loadAllMayorEvents() {
        this.mayorEventService
            .query({
                page: 0,
                size: 1000,
                rehabilitationId: this.global.rehabCenter
            })
            .subscribe((res: HttpResponse<[]>) => this.paginateMayorEvents(res.body, res.headers));
    }
    protected paginateMayorEvents(data: [], headers: HttpHeaders) {
        this.mayorEvents = [];
        for (let i = 0; i < data.length; i++) {
            this.mayorEvents.push(data[i]);
        }
        this.graphicEventsPerSessions = this.mayorEvents;
    }

    randomColor(i) {
        const colors = [
            '#455a64',
            '#d84315',
            '#ff8f00',
            '#004d40',
            '#0277bd',
            '#1e88e5',
            '#283593',
            '#c62828',
            '#5375f2',
            '#1c0365',
            '#14a9ad',
            '#4ca2f9',
            '#a4e43f',
            '#d298e2',
            '#6119d0',
            '#d2737d',
            '#c0a43c',
            '#f2510e',
            '#651be6',
            '#79806e',
            '#61da5e',
            '#cd2f00',
            '#9348af',
            '#01ac53',
            '#c5a4fb',
            '#996635',
            '#b11573',
            '#4bb473',
            '#75d89e',
            '#2f3f94',
            '#2f7b99',
            '#da967d',
            '#34891f',
            '#b0d87b',
            '#ca4751',
            '#7e50a8',
            '#c4d647',
            '#e0eeb8',
            '#11dec1',
            '#289812',
            '#566ca0',
            '#ffdbe1',
            '#2f1179',
            '#935b6d',
            '#916988',
            '#513d98',
            '#aead3a',
            '#9e6d71',
            '#4b5bdc',
            '#0cd36d',
            '#250662',
            '#cb5bea',
            '#228916',
            '#ac3e1b',
            '#df514a',
            '#539397',
            '#880977',
            '#f697c1',
            '#ba96ce',
            '#679c9d',
            '#c6c42c',
            '#5d2c52',
            '#48b41b',
            '#e1cf3b',
            '#5be4f0',
            '#57c4d8',
            '#a4d17a',
            '#225b8',
            '#be608b',
            '#96b00c',
            '#088baf',
            '#f158bf',
            '#e145ba',
            '#ee91e3',
            '#05d371',
            '#5426e0',
            '#4834d0',
            '#802234',
            '#6749e8',
            '#0971f0',
            '#8fb413',
            '#b2b4f0',
            '#c3c89d',
            '#c9a941',
            '#41d158',
            '#fb21a3',
            '#51aed9',
            '#5bb32d',
            '#807fb',
            '#21538e',
            '#89d534',
            '#d36647',
            '#7fb411',
            '#0023b8',
            '#3b8c2a',
            '#986b53',
            '#f50422',
            '#983f7a',
            '#ea24a3',
            '#79352c',
            '#521250',
            '#c79ed2',
            '#d6dd92',
            '#e33e52',
            '#b2be57',
            '#fa06ec',
            '#1bb699',
            '#6b2e5f',
            '#64820f',
            '#1c271',
            '#21538e',
            '#89d534',
            '#d36647',
            '#7fb411',
            '#0023b8',
            '#3b8c2a',
            '#986b53',
            '#f50422',
            '#983f7a',
            '#ea24a3',
            '#79352c',
            '#521250',
            '#c79ed2',
            '#d6dd92',
            '#e33e52',
            '#b2be57',
            '#fa06ec',
            '#1bb699',
            '#6b2e5f',
            '#64820f',
            '#1c271',
            '#9cb64a',
            '#996c48',
            '#9ab9b7',
            '#06e052',
            '#e3a481',
            '#0eb621',
            '#fc458e',
            '#b2db15',
            '#aa226d',
            '#792ed8',
            '#73872a',
            '#520d3a',
            '#cefcb8',
            '#a5b3d9',
            '#7d1d85',
            '#c4fd57',
            '#f1ae16',
            '#8fe22a',
            '#ef6e3c',
            '#243eeb',
            '#1dc18',
            '#dd93fd',
            '#3f8473',
            '#e7dbce',
            '#421f79',
            '#7a3d93',
            '#635f6d',
            '#93f2d7',
            '#9b5c2a',
            '#15b9ee',
            '#0f5997',
            '#409188',
            '#911e20',
            '#1350ce',
            '#10e5b1',
            '#fff4d7',
            '#cb2582',
            '#ce00be',
            '#32d5d6',
            '#17232',
            '#608572',
            '#c79bc2',
            '#00f87c',
            '#77772a',
            '#6995ba',
            '#fc6b57',
            '#f07815',
            '#8fd883',
            '#060e27',
            '#96e591',
            '#21d52e',
            '#d00043',
            '#b47162',
            '#1ec227',
            '#4f0f6f',
            '#1d1d58',
            '#947002',
            '#bde052',
            '#e08c56',
            '#28fcfd',
            '#bb09b',
            '#36486a',
            '#d02e29',
            '#1ae6db',
            '#3e464c',
            '#a84a8f',
            '#911e7e',
            '#3f16d9',
            '#0f525f',
            '#ac7c0a',
            '#b4c086',
            '#c9d730',
            '#30cc49',
            '#3d6751',
            '#fb4c03',
            '#640fc1',
            '#62c03e',
            '#d3493a',
            '#88aa0b',
            '#406df9',
            '#615af0',
            '#4be47',
            '#2a3434',
            '#4a543f',
            '#79bca0',
            '#a8b8d4',
            '#00efd4',
            '#7ad236',
            '#7260d8',
            '#1deaa7',
            '#06f43a',
            '#823c59',
            '#e3d94c',
            '#dc1c06',
            '#f53b2a',
            '#b46238',
            '#2dfff6',
            '#a82b89',
            '#1a8011',
            '#436a9f',
            '#1a806a',
            '#4cf09d',
            '#c188a2',
            '#67eb4b',
            '#b308d3',
            '#fc7e41',
            '#af3101',
            '#ff065',
            '#71b1f4',
            '#a2f8a5',
            '#e23dd0',
            '#d3486d',
            '#00f7f9',
            '#474893',
            '#3cec35',
            '#1c65cb',
            '#5d1d0c',
            '#2d7d2a',
            '#ff3420',
            '#5cdd87',
            '#a259a4',
            '#e4ac44',
            '#1bede6',
            '#8798a4',
            '#d7790f',
            '#b2c24f',
            '#de73c2',
            '#d70a9c',
            '#25b67',
            '#88e9b8',
            '#c2b0e2',
            '#86e98f',
            '#ae90e2',
            '#1a806b',
            '#436a9e',
            '#0ec0ff',
            '#f812b3',
            '#b17fc9',
            '#8d6c2f',
            '#d3277a',
            '#2ca1ae',
            '#9685eb',
            '#8a96c6',
            '#dba2e6',
            '#76fc1b',
            '#608fa4',
            '#20f6ba',
            '#07d7f6',
            '#dce77a',
            '#77ecca'
        ];
        if (i > colors.length) {
            return (
                '#' +
                Math.random()
                    .toString(16)
                    .substr(-6)
            );
        }
        return colors[i];
    }
    previousState() {
        window.history.back();
    }
}
