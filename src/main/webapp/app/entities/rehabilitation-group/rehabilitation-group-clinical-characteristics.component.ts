import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { filter, map } from 'rxjs/operators';
import { RehabilitationGroupService } from 'app/entities/rehabilitation-group/rehabilitation-group.service';

@Component({
    selector: 'jhi-rehabilitation-group-clinical-characteristics',
    templateUrl: './rehabilitation-group-clinical-characteristics.component.html'
})
export class RehabilitationGroupClinicalCharacteristicsComponent implements OnInit {
    rehabilitationGroup: any;
    characteristics: any;
    ready = false;

    genderGraphic: any;
    scholarityGraphic: any;
    imcGraphic: any;
    cardioVascularRiskGraphic: any;
    comorbiditiesGraphic: any;
    incomeDiagnosisGraphic: any;

    constructor(route: ActivatedRoute, protected service: RehabilitationGroupService) {
        const id = route.snapshot.params['id'];
        if (id) {
            this.service
                .findClinicalCharasteristics(id)
                .pipe(
                    filter(response => response.ok),
                    map(characteristics => characteristics.body)
                )
                .subscribe(data => {
                    this.characteristics = data;
                    this.rehabilitationGroup = this.characteristics.rehabilitationGroupDTO;
                    this.genderGraphic = {
                        options: {},
                        labels: [],
                        type: 'pie',
                        legend: true,
                        colors: [],
                        data: []
                    };
                    this.scholarityGraphic = {
                        options: {},
                        labels: [],
                        type: 'pie',
                        legend: true,
                        colors: [],
                        data: []
                    };
                    this.imcGraphic = {
                        options: {},
                        labels: [],
                        type: 'pie',
                        legend: true,
                        colors: [],
                        data: []
                    };
                    this.cardioVascularRiskGraphic = {
                        options: {},
                        labels: [],
                        type: 'pie',
                        legend: true,
                        colors: [],
                        data: []
                    };
                    this.comorbiditiesGraphic = {
                        options: {},
                        labels: [],
                        type: 'pie',
                        legend: true,
                        colors: [],
                        data: []
                    };
                    this.incomeDiagnosisGraphic = {
                        options: {},
                        labels: [],
                        type: 'pie',
                        legend: true,
                        colors: [],
                        data: []
                    };
                    this.renderGenderGraphic();
                    this.renderScholarityGraphic();
                    this.renderIMCGraphic();
                    this.renderCardioVascularRiskGraphic();
                    this.renderComorbiditesGraphic();
                    this.renderIncomeDiagnosisGraphic();
                });
        }
    }

    renderCardioVascularRiskGraphic() {
        this.cardioVascularRiskGraphic.options = {
            responsive: true,
            legend: {
                position: 'left'
            },
            plugins: {
                datalabels: {}
            }
        };
        //        '0 - Alto', '1 - Moderado', '2 - Bajo'
        this.cardioVascularRiskGraphic.labels.push('Alto');
        this.cardioVascularRiskGraphic.labels.push('Moderado');
        this.cardioVascularRiskGraphic.labels.push('Bajo');
        this.cardioVascularRiskGraphic.colors.push({
            backgroundColor: ['#ff2e2a', '#ff8343', '#ffea16']
        });
        for (let j = 0; j < this.characteristics.cardiovascularRiskDistribution.length; j++) {
            this.cardioVascularRiskGraphic.data.push(this.characteristics.cardiovascularRiskDistribution[j]);
        }
        this.ready = true;
    }

    renderIMCGraphic() {
        this.imcGraphic.options = {
            responsive: true,
            legend: {
                position: 'left'
            },
            plugins: {
                datalabels: {}
            }
        };
        //     -Promedio de IMC  en rangos de [0](-19)delgadez  [1](20-25)normal  [2](26-30)sobrepeso [3](30 o mas) obesidad
        this.imcGraphic.labels.push('Delgadez');
        this.imcGraphic.labels.push('Normal');
        this.imcGraphic.labels.push('Sobrepeso');
        this.imcGraphic.labels.push('Obesidad');

        this.imcGraphic.colors.push({
            backgroundColor: ['#80cbc4', '#00897b', '#00695c', '#004d40']
        });
        for (let j = 0; j < this.characteristics.distributionIMC.length; j++) {
            this.imcGraphic.data.push(this.characteristics.distributionIMC[j]);
        }
        this.ready = true;
    }

    renderScholarityGraphic() {
        this.scholarityGraphic.options = {
            responsive: true,
            legend: {
                position: 'left'
            },
            plugins: {
                datalabels: {}
            }
        };
        //            '0 - Ninguna',
        //            '1 - Primaria Incompleta',
        //            '2 - Primaria completa',
        //            '3 - Secundaria Incompleta',
        //            '4 - Secundaria completa',
        //            '5 - Universitaria Incompleta',
        //            '6 - Universitaria completa',
        //            '7 - Parauniversitaria'
        this.scholarityGraphic.labels.push('Ninguna');
        this.scholarityGraphic.labels.push('Primaria Incompleta');
        this.scholarityGraphic.labels.push('Primaria completa');
        this.scholarityGraphic.labels.push('Secundaria Incompleta');
        this.scholarityGraphic.labels.push('Secundaria completa');
        this.scholarityGraphic.labels.push('Universitaria Incompleta');
        this.scholarityGraphic.labels.push('Universitaria completa');
        this.scholarityGraphic.labels.push('Parauniversitaria');
        this.scholarityGraphic.colors.push({
            backgroundColor: ['#bbdefb', '#90caf9', '#64b5f6', '#42a5f5', '#0277bd', '#2196f3', '#1e88e5', '#0d47a1']
        });
        this.scholarityGraphic.colors.push({
            backgroundColor: []
        });
        for (let j = 0; j < this.characteristics.scholarityDistribution.length; j++) {
            this.scholarityGraphic.data.push(this.characteristics.scholarityDistribution[j]);
        }
        this.ready = true;
    }

    renderGenderGraphic() {
        this.genderGraphic.options = {
            responsive: true,
            legend: {
                position: 'left'
            },
            plugins: {
                datalabels: {}
            }
        };
        this.genderGraphic.colors.push({
            backgroundColor: ['#526aff', '#ff9dea']
        });

        this.genderGraphic.labels.push('Masculino');
        this.genderGraphic.labels.push('Femenino');
        for (let j = 0; j < this.characteristics.genderDistribution.length; j++) {
            this.genderGraphic.data.push(this.characteristics.genderDistribution[j]);
        }
        this.ready = true;
    }

    renderComorbiditesGraphic() {
        this.comorbiditiesGraphic.options = {
            responsive: true,
            legend: {
                position: 'left'
            },
            plugins: {
                datalabels: {}
            }
        };

        this.comorbiditiesGraphic.colors.push({
            backgroundColor: []
        });
        for (let j = 0; j < this.characteristics.comorbiditieDistribution.length; j++) {
            this.comorbiditiesGraphic.labels.push(this.characteristics.comorbiditieDistribution[j].description);
            this.comorbiditiesGraphic.data.push(this.characteristics.comorbiditieDistribution[j].rehabilitationCenterId);
            this.comorbiditiesGraphic.colors[0].backgroundColor.push(this.randomColor(j));
        }
        this.ready = true;
    }

    renderIncomeDiagnosisGraphic() {
        this.incomeDiagnosisGraphic.options = {
            responsive: true,
            legend: {
                position: 'left'
            },
            plugins: {
                datalabels: {}
            }
        };
        this.incomeDiagnosisGraphic.colors.push({
            backgroundColor: []
        });
        for (let j = 0; j < this.characteristics.incomeDiagnosisDistribution.length; j++) {
            this.incomeDiagnosisGraphic.labels.push(this.characteristics.incomeDiagnosisDistribution[j].description);
            this.incomeDiagnosisGraphic.data.push(this.characteristics.incomeDiagnosisDistribution[j].rehabilitationCenterId);
            this.incomeDiagnosisGraphic.colors[0].backgroundColor.push(this.randomColor(j));
        }
        this.ready = true;
    }

    ngOnInit() {
        // this.activatedRoute.data.subscribe(({ rehabilitationGroup }) => {
        //   this.rehabilitationGroup = rehabilitationGroup;
        //
        //   this.renderGraphMinorDistribution(this.rehabilitationGroup);
        // });
    }

    randomInt(min, max) {
        return Math.floor(Math.random() * (max - min + 1)) + min;
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
